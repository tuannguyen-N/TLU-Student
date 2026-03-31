//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import SwiftUI
import Foundation
import Shared

@MainActor
class LoginViewModel: ObservableObject {
    @Published var uiState = LoginState()
    @Published var event: LoginUiEvent?

    private let loginUseCase: LoginUseCase

    init(loginUseCase: LoginUseCase){
        self.loginUseCase = loginUseCase
    }

    func onLoginClick(viewController: UIViewController){
        MsalHelper.shared.signIn(viewController: viewController, completion: {
            [weak self] token in

            guard let self = self else {return}

            if let token = token {
                self.onSignMsalSuccess(token: token)
            }else {
                self.updateState{
                    $0.showErrorSheet = true
                }
            }
        })
    }

    func onSignMsalSuccess(token: String) {
        Task {
            updateState { $0.isLoading = true }
            defer { updateState { $0.isLoading = false } }

            let result = try await loginUseCase.invoke(microsoftAccessToken: token)

            result.onSuccess { _ in
                print("Successfully logged in")
                DispatchQueue.main.async {
                    self.sendEvent(.navigateToHome)
                }
            }

            result.onFailure { failure in
                print("Failed to login: \(failure)")
                DispatchQueue.main.async {
                    let message = failure.message ?? "Unknown error"
                    print(message)
                    self.updateState { $0.showErrorSheet = true }
                }
            }
        }
    }
    
    func onDismissErrorSheet(){
        updateState{ $0.showErrorSheet = false}
    }
    
    private func updateState(_ block: (inout LoginState) -> Void){
        block(&uiState)
    }
    
    private func sendEvent(_ event: LoginUiEvent){
        self.event = event
    }
}
