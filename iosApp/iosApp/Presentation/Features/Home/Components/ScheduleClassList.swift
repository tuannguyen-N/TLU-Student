//
//  ScheduleClassList.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import SwiftUI
import Shared

struct ScheduleClassList: View {
    var courseClasses: [CourseClass]?

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            
            HStack {
                Text("Lịch học hôm nay")
                    .appTextStyle(.titleMedium)
                    .fontWeight(.semibold)
                
                Spacer()
            }
            .padding(.bottom, 15)

            
            if let courseClasses = courseClasses {
                
                let currentTime = Date()
                
                ForEach(courseClasses.indices, id: \.self) { index in
                    let item = courseClasses[index]
                    
                    if item.isGoing(currentTime: currentTime) {
                        ScheduleCurrent(item: item)
                    } else {
                        ScheduleNext(
                            item: item,
                            currentTime: currentTime,
                            isFirstItem: index == 0
                        )
                    }
                }
            }
        }
    }
}
