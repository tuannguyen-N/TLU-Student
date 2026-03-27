// swift-tools-version: 5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "iosApp",
    platforms: [
        .iOS(.v15)
    ],
    products: [
        .library(
            name: "iosApp",
            targets: ["iosApp"]
        ),
    ],
    dependencies: [
        .package(url: "https://github.com/AzureAD/microsoft-authentication-library-for-objc.git", from: "1.2.0")
    ],
    targets: [
        .target(
            name: "iosApp",
            dependencies: [
                .product(name: "MSAL", package: "microsoft-authentication-library-for-objc")
            ]
        ),
    ]
)
