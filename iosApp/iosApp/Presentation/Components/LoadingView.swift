//
//  LoadingView.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import SwiftUI
import Lottie

struct LoadingView: View {
    var body: some View {
        ZStack{
            Color.black.opacity(0.4).ignoresSafeArea()
            
            LottieView(animation: .named("loading_splash"))
                .playing(loopMode: .loop)
                .frame(width: 80, height: 80)
        }
        .allowsHitTesting(false)
    }
}

#Preview {
    LoadingView()
}
