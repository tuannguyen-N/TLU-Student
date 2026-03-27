import SwiftUI
import Shared

struct AppRootView: View {
    @StateObject private var router = AppRouter()
    @Environment(\.appContainer) var appContainer
    
    var body: some View {
        router.currentView(appContainer: appContainer)
            .environmentObject(router)
    }
}
