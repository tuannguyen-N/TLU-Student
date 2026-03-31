//
//  ScheduleNext.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import SwiftUI
import Shared

struct ScheduleNext: View {
    var item: CourseClass
    var currentTime: Date
    var isFirstItem: Bool = false
    
    var body: some View {
        HStack(alignment: .top, spacing: 0) {
            
            ZStack(alignment: .leading) {
                VStack(alignment: .trailing, spacing: 2) {
                    Text(item.startTime.toHourMinuteAmPm())
                        .appTextStyle(.titleMedium,color: Color.gray)
                    
                    Text(item.endTime.toHourMinuteAmPm())
                        .appTextStyle(.bodyMedium,color: Color.gray)
                }
                .padding(.top, 15)
            }.frame(width: 80)
            
            Spacer().frame(width: 24)
            
            TimelineView(isFirstItem: isFirstItem)
            VStack(alignment: .leading, spacing: 0) {
                
                Text(item.getStatusText(currentTime: currentTime))
                    .appTextStyle(.bodySmall, color: Color.red)
                    .fontWeight(.semibold)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 3)
                    .background(Color.red.opacity(0.15))
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                
                
                Text(item.subjectName)
                    .appTextStyle(.titleMedium, color: Color.gray)
                    .padding(.top, 8)
                    .padding(.bottom, 5)
                
                
                HStack(spacing: 3) {
                    Image("icon_location")
                        .renderingMode(.template)
                        .foregroundColor(Color.gray)
                    
                    Text(item.room)
                        .appTextStyle(.bodySmall, color: Color.gray)
                    
                    Circle()
                        .fill(Color.gray)
                        .frame(width: 3, height: 3)
                    
                    Text("Toà \(item.room.first ?? "A")")
                        .appTextStyle(.bodySmall, color: Color.gray)
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.horizontal, 12)
            .padding(.vertical, 10)
            .background(Color(white: 0.97))
            .clipShape(RoundedRectangle(cornerRadius: 12))
            .shadow(radius: 2)
            .padding(.top, 15)
        }
    }
}
