//
//  CenterContent.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import SwiftUI

struct CenterContent: View {
    
    var body: some View {
        VStack(spacing: 0) {
            
            Image("tlu_logo")
                .resizable()
                .scaledToFit()
                .frame(height: 60)
                .padding(.bottom, 10)
            
            Text("CỔNG THÔNG TIN ĐÀO TẠO")
                .appTextStyle(.titleMedium)
                .fontWeight(.bold)
            .foregroundColor(Color.mainBlue)
        }
    }
}
