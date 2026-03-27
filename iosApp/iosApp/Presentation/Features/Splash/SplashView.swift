//
// Created by Tuan Nguyen on 3/20/26.
//

import SwiftUI

struct SplashView: View {
    @EnvironmentObject var router: AppRouter
    @State private var scale: CGFloat = 0.6
    
    var body: some View {
        ZStack{
            Color.white.ignoresSafeArea()
            
            Image("tlu_logo")
                .resizable()
                .scaledToFit()
                .frame(width: 200)
                .scaleEffect(scale)
        }
        .onAppear{
            animate()
        }
    }
    
    private func animate(){
        withAnimation(.interpolatingSpring(stiffness: 170, damping: 8)){
            scale = 1.0
        }
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 3.0) {
            router.resetTo(.login)
        }
    }
}
