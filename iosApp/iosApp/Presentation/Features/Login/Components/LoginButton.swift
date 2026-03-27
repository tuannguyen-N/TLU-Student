//
//  LoginButton.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import SwiftUI

struct LoginButton: View {
    var onLogin: () -> Void
    var body: some View {
        Button(action: {
            onLogin()
        }){
            HStack(spacing: 8, content: {
                Image("logo_microsofts")
                    .resizable()
                    .frame(width: 20, height: 20)
                
                Text("Đăng nhập bằng microsoft")
                    .font(.system(size: 16))
                    .foregroundColor(.white)
            })
            .padding()
            .frame(maxWidth: .infinity)
            .background(Color.mainRed)
            .cornerRadius(16)
        }
        .padding(.horizontal, 40)
    }
}

#Preview {
    LoginButton(onLogin: {
        
    })
}
