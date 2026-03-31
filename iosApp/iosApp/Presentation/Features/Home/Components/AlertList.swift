//
//  AlertList.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import SwiftUI
import Shared

struct AlertList: View {
    var items: [AlertUiModel]
    var isLoading: Bool = false
    var onClickAction: () -> Void = {}

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: 10) {
                if isLoading {
                    ForEach(0..<3, id: \.self) { index in
                        RoundedRectangle(cornerRadius: 12)
                            .fill(Color.gray.opacity(0.3))
                            .frame(width: 300, height: 130)
                            .redacted(reason: .placeholder)
                    }
                } else {
                    ForEach(Array(items.enumerated()), id: \.offset) { _, item in
                        
                        if item.isHighAlert {
                            HighAlertCard(
                                item: item,
                                onClickAction: onClickAction
                            )
                        } else {
                            MediumAlertCard(
                                item: item,
                                onClickAction: onClickAction
                            )
                        }
                    }
                }
            }
        }
        .frame(maxWidth: .infinity)
    }
}
