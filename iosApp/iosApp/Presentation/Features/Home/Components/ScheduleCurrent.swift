//
//  ScheduleCurrent.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//
import SwiftUI
import Shared

struct ScheduleCurrent: View {
    var item: CourseClass
    var isFirstItem: Bool = false
    
    var body: some View {
        HStack(alignment: .top, spacing: 0) {
            
            ZStack(alignment: .leading) {
                VStack(alignment: .trailing, spacing: 2) {
                    Text(item.startTime.toHourMinuteAmPm())
                        .appTextStyle(.titleMedium)
                        .fontWeight(.bold)
                    
                    Text(item.endTime.toHourMinuteAmPm())
                        .appTextStyle(.bodyMedium)
                }
                .padding(.top, 15)
            }
            .frame(width: 80)
            
            Spacer().frame(width: 24)
            
            CurrentTimelineView(isFirstItem: isFirstItem)
            
            VStack(spacing: 0) {
                
                Text("Đang diễn ra")
                    .appTextStyle(.bodySmall, color: .green)
                    .fontWeight(.semibold)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 3)
                    .background(Color.green.opacity(0.15))
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                
                
                Text(item.subjectName)
                    .appTextStyle(.titleMedium)
                    .fontWeight(.semibold)
                    .padding(.top, 8)
                    .padding(.bottom, 5)
                
                HStack(spacing: 3) {
                    Image("icon_location")
                    
                    Text(item.room)
                        .appTextStyle(.bodySmall, color: .gray)
                    
                    Circle()
                        .fill(Color.red)
                        .frame(width: 3, height: 3)
                    
                    Text("Toà \(item.room.first ?? "A")")
                        .appTextStyle(.bodySmall, color: .gray)
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
