//
//  LoginView.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import SwiftUI
import Shared

struct LoginView: View {
    @EnvironmentObject var router: AppRouter
    @StateObject var viewModel: LoginViewModel
    
    init(loginUseCase: LoginUseCase) {
        _viewModel = StateObject(wrappedValue: LoginViewModel(loginUseCase: loginUseCase))
    }
    
    var body: some View {
        ZStack{
            Color.white.ignoresSafeArea()
            
            CenterContent()
            
            VStack{
                Spacer()
                
                VStack(spacing: 0) {
                    
                    LoginButton {
                        guard let windowScene = UIApplication.shared.connectedScenes
                            .first as? UIWindowScene,
                              let rootVC = windowScene.windows.first?.rootViewController else {
                            return
                        }
                        viewModel.onLoginClick(viewController: rootVC)
                    }
                    
                    Divider()
                        .padding(.top, 20)
                        .padding(.bottom, 10)
                        .padding(.horizontal, 40)
                    
                    Text("Hướng dẫn sử dụng")
                        .appTextStyle(.titleSmall, color: .black)
                        .padding(.bottom, 0)
                        .onTapGesture {
                            // TODO
                        }
                }
            }
            .overlay {
                if viewModel.uiState.isLoading {
                    LoadingView()
                }
            }
            .onReceive(viewModel.$event.compactMap { $0 }) { event in
                switch event {
                case .navigateToHome:
                    router.resetTo(.home)
                }
            }
            .sheet(isPresented: $viewModel.uiState.showErrorSheet) {
                AuthenticationErrorBottomSheet(
                    onRetry: {
                        viewModel.uiState.showErrorSheet = false
                    }
                )
                .presentationDetents([.height(300)])
                .presentationDragIndicator(.visible)
                .presentationBackground(Color.white)
            }
        }
    }
}
