//
//  AppStrings.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import Foundation

extension String {
    func toHourMinuteAmPmFast() -> String {
        let parts = self.split(separator: ":")
        guard parts.count == 2,
              let hour = Int(parts[0]),
              let minute = Int(parts[1]) else {
            return self
        }

        let hour12: Int
        if hour == 0 {
            hour12 = 12
        } else if hour > 12 {
            hour12 = hour - 12
        } else {
            hour12 = hour
        }

        let amPm = hour < 12 ? "AM" : "PM"

        return String(format: "%02d:%02d %@", hour12, minute, amPm)
    }
    
    func toHourMinuteAmPm() -> String {
            let parts = self.split(separator: ":")
            guard parts.count == 2,
                  let hour = Int(parts[0]),
                  let minute = Int(parts[1]) else {
                return self
            }

            let hour12: Int
            if hour == 0 {
                hour12 = 12
            } else if hour > 12 {
                hour12 = hour - 12
            } else {
                hour12 = hour
            }

            let amPm = hour < 12 ? "AM" : "PM"

            return String(format: "%02d:%02d %@", hour12, minute, amPm)
        }
    
    func toHourMinute() -> String {
            let parts = self.split(separator: ":")
            guard parts.count == 2,
                  let hour = Int(parts[0]),
                  let minute = Int(parts[1]) else {
                return self
            }
            
            return String(format: "%02d:%02d", hour, minute)
        }
    
    func toSlashDate() -> String {
            return self.replacingOccurrences(of: "-", with: "/")
        }
}
