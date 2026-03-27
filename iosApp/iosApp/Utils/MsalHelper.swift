//
//  MsalHelper.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import Foundation
import MSAL
import UIKit

class MsalHelper {

    static let shared = MsalHelper()

    private var applicationContext: MSALPublicClientApplication?
    
    private let clientId = "77df993d-e728-4b8e-9770-6b409aa99552"
    private let authority = "https://login.microsoftonline.com/aec003eb-c537-4e8a-b0f3-1144a93a60bc"
    private let scopes = ["api://77df993d-e728-4b8e-9770-6b409aa99552/access_as_user"]

    private init() {}

    func initMsal(completion: @escaping (Bool) -> Void) {
        print("📦 Bundle ID:", Bundle.main.bundleIdentifier ?? "nil")
        do {
            let authorityURL = try MSALAADAuthority(url: URL(string: authority)!)
            let config = MSALPublicClientApplicationConfig(
                clientId: clientId,
                redirectUri: nil,
                authority: authorityURL
            )
            self.applicationContext = try MSALPublicClientApplication(configuration: config)
            completion(true)
        } catch {
            completion(false)
        }
    }

    func signIn(viewController: UIViewController,
                completion: @escaping (String?) -> Void) {

        guard let app = applicationContext else {
            completion(nil)
            return
        }

        let webParameters = MSALWebviewParameters(authPresentationViewController: viewController)
        let parameters = MSALInteractiveTokenParameters(scopes: scopes, webviewParameters: webParameters)

        app.acquireToken(with: parameters) { result, error in
            if let error = error {
                print("Login error: \(error.localizedDescription)")
                completion(nil)
                return
            }

            guard let result = result else {
                completion(nil)
                return
            }

            let accessToken = result.accessToken
            print("Access Token: \(accessToken)")
            completion(accessToken)
        }
    }

    func checkExistingAccount(completion: @escaping (MSALAccount?, String?) -> Void) {

        guard let app = applicationContext else {
            completion(nil, nil)
            return
        }

        do {
            let accounts = try app.allAccounts()
            
            guard let account = accounts.first else {
                completion(nil, nil)
                return
            }

            let parameters = MSALSilentTokenParameters(
                scopes: scopes,
                account: account
            )

            app.acquireTokenSilent(with: parameters) { result, error in
                if let result = result {
                    completion(result.account, result.accessToken)
                } else {
                    completion(nil, nil)
                }
            }

        } catch {
            print("Get account error: \(error.localizedDescription)")
            completion(nil, nil)
        }
    }

    func signOut(completion: @escaping (Bool) -> Void) {

        guard let app = applicationContext else {
            completion(false)
            return
        }

        do {
            let accounts = try app.allAccounts()

            if let account = accounts.first {
                try app.remove(account)
                print("Logged out")
                completion(true)
            } else {
                completion(false)
            }
        } catch {
            print("Logout error: \(error.localizedDescription)")
            completion(false)
        }
    }
}
