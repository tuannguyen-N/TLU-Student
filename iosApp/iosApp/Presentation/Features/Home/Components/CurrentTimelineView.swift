//
//  CurrentTimelineView.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import SwiftUI

struct CurrentTimelineView: View {
    var isFirstItem: Bool

    var body: some View {
        GeometryReader { geo in
            let height = geo.size.height
            
            Canvas { context, size in
                let dotRadius: CGFloat = 3.5
                let x: CGFloat = 6
                
                let topOffset: CGFloat = 8
                
                let startY = isFirstItem
                    ? topOffset + dotRadius * 2
                    : 0
                
                let endY = height
                
                var path = Path()
                path.move(to: CGPoint(x: x, y: startY))
                path.addLine(to: CGPoint(x: x, y: endY))
                context.stroke(path, with: .color(Color.gray), lineWidth: 1)
                
                let rect = CGRect(
                    x: x - dotRadius,
                    y: topOffset,
                    width: dotRadius * 2,
                    height: dotRadius * 2
                )
                context.fill(Path(ellipseIn: rect), with: .color(Color.green))
            }
        }
        .frame(width: 20)
    }
}
