//
//  AppRouter.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/20/26.
//

import Foundation
import SwiftUI

@MainActor
class AppRouter: ObservableObject{
    @Published var path = NavigationPath()
    @Published var root: AppRoute = .splash
    
    func push(_ route: AppRoute){
        path.append(route)
    }
    
    func pop(){
        path.removeLast()
    }
    
    func popToRoot(){
        path.removeLast(path.count)
    }
    
    func setRoot(_ route: AppRoute){
        path = NavigationPath()
        root = route
    }
}
