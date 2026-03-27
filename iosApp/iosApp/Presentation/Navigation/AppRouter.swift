//
//  AppRouter.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/20/26.
//

import Foundation
import SwiftUI
import Shared

class AppRouter: ObservableObject {
    @Published var route: AppRoute = .splash
    
    func resetTo(_ newRoute: AppRoute) {
        route = newRoute
    }
    
    @ViewBuilder
    func currentView(appContainer: AppContainer?) -> some View {
        switch route {
        case .splash:
            SplashView()
        case .login:
            if let useCase = appContainer?.loginUseCase {
                LoginView(loginUseCase: useCase)
            } else {
                Text("Error: AppContainer missing")
            }
        }
    }
}
