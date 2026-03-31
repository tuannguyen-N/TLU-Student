//
//  TimeFormat.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import Foundation
import Shared

var today: Date {
    return Date()
}

func getTodayDayOfWeek() -> Int {
    let calendar = Calendar.current
    let weekday = calendar.component(.weekday, from: today)
    
    // Swift: 1 = Sunday → convert sang ISO (1 = Monday)
    return weekday == 1 ? 7 : weekday - 1
}

extension Int64 {
    func toFormatTime() -> String {
        let date = Date(timeIntervalSince1970: TimeInterval(self) / 1000)
        
        let formatter = DateFormatter()
        formatter.dateFormat = "hh:mm a"
        
        return formatter.string(from: date)
    }
}


func getWeekOf(date: Date) -> (String, String) {
    let calendar = Calendar.current
    
    let weekday = getTodayDayOfWeek()
    
    let start = calendar.date(byAdding: .day, value: -(weekday - 1), to: date)!
    let end = calendar.date(byAdding: .day, value: (7 - weekday), to: date)!
    
    let formatter = DateFormatter()
    formatter.dateFormat = "yyyy-MM-dd"
    
    return (formatter.string(from: start), formatter.string(from: end))
}

func getCurrentWeek() -> (String, String) {
    return getWeekOf(date: today)
}

func getNextWeek(date: Date) -> (String, String) {
    let next = Calendar.current.date(byAdding: .day, value: 7, to: date)!
    return getWeekOf(date: next)
}

func getPreviousWeek(date: Date) -> (String, String) {
    let prev = Calendar.current.date(byAdding: .day, value: -7, to: date)!
    return getWeekOf(date: prev)
}

extension CourseClass {
    func isGoing(currentTime: Date = Date()) -> Bool {
        let calendar = Calendar.current
        
        let now = calendar.component(.hour, from: currentTime) * 60 +
                  calendar.component(.minute, from: currentTime)
        
        let startParts = startTime.split(separator: ":")
        let endParts = endTime.split(separator: ":")
        
        guard let sh = Int(startParts[0]),
              let sm = Int(startParts[1]),
              let eh = Int(endParts[0]),
              let em = Int(endParts[1]) else {
            return false
        }
        
        let start = sh * 60 + sm
        let end = eh * 60 + em
        
        return now >= start && now <= end
    }
    
    func getStatusText(currentTime: Date = Date()) -> String {
            let calendar = Calendar.current
            
            let currentMinutes =
                calendar.component(.hour, from: currentTime) * 60 +
                calendar.component(.minute, from: currentTime)
            
            let startParts = startTime.split(separator: ":")
            let endParts = endTime.split(separator: ":")
            
            guard let sh = Int(startParts[0]),
                  let sm = Int(startParts[1]),
                  let eh = Int(endParts[0]),
                  let em = Int(endParts[1]) else {
                return ""
            }
            
            let startMinutes = sh * 60 + sm
            let endMinutes = eh * 60 + em
            
            if currentMinutes > endMinutes {
                return "Đã kết thúc"
            } else if currentMinutes >= startMinutes && currentMinutes <= endMinutes {
                return "Đang diễn ra"
            } else if startMinutes - currentMinutes <= 60 {
                let diff = startMinutes - currentMinutes
                return "Sau \(diff) phút"
            } else {
                return "Sắp diễn ra"
            }
        }
}
