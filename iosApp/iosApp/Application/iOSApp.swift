import SwiftUI
import Shared

@main
struct iOSApp: App {
    let appContainer = AppContainer(
        tokenStorage: IosTokenStorage(),
        context: nil
    )
    
    init() {
        MsalHelper.shared.initMsal { _ in }
    }
    
    var body: some Scene {
        WindowGroup {
            AppRootView()
                .environment(\.appContainer, appContainer)
        }
    }
}

private struct AppContainerKey: EnvironmentKey {
    static let defaultValue: AppContainer? = nil
}

extension EnvironmentValues {
    var appContainer: AppContainer? {
        get { self[AppContainerKey.self] }
        set { self[AppContainerKey.self] = newValue }
    }
}
