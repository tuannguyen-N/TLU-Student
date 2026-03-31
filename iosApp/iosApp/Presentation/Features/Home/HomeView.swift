//
//  HomeView.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import SwiftUI
import Shared

struct HomeView: View {
    let demoAlerts: [AlertUiModel] = [
            AlertUiModel(
                title: "Nộp học phí kỳ 2",
                content: "Hạn cuối thanh toán học phí là 25/08. Vui lòng hoàn tất để không bị huỷ đăng ký môn học.",
                isHighAlert: true
            ),
            AlertUiModel(
                title: "Lịch thi học kỳ",
                content: "Đã có lịch thi chính thức cho các môn chuyên ngành, vui lòng kiểm tra trên cổng thông tin sinh viên.",
                isHighAlert: false
            ),
            AlertUiModel(
                title: "Thông báo nghỉ lễ",
                content: "Sinh viên được nghỉ học từ ngày 30/04 đến hết ngày 01/05.",
                isHighAlert: false
            )
        ]
    
    let demoClasses: [CourseClass] = [
        
        CourseClass(
            classCode: "INT2201",
            dayOfWeek: 2,
            endPeriod: 3,
            endTime: "10:30",
            room: "A101",
            startPeriod: 1,
            startTime: "11:00",
            subjectCode: "INT2201",
            subjectName: "Lập trình di động",
            lecturer: Lecturer_(
                lecturerCode: "GV001",
                fullName: "Nguyễn Văn B",
                phoneNumber: "0123456789",
                email: "b.nguyen@edu.vn"
            )
        ),
        
        CourseClass(
            classCode: "INT2202",
            dayOfWeek: 2,
            endPeriod: 6,
            endTime: "13:30",
            room: "B202",
            startPeriod: 4,
            startTime: "11:30",
            subjectCode: "INT2202",
            subjectName: "Cấu trúc dữ liệu",
            lecturer: Lecturer_(
                lecturerCode: "GV002",
                fullName: "Trần Thị C",
                phoneNumber: nil,
                email: "c.tran@edu.vn"
            )
        ),
        
        CourseClass(
            classCode: "INT2203",
            dayOfWeek: 2,
            endPeriod: 9,
            endTime: "16:30",
            room: "C303",
            startPeriod: 7,
            startTime: "14:00",
            subjectCode: "INT2203",
            subjectName: "Hệ điều hành",
            lecturer: Lecturer_(
                lecturerCode: "GV003",
                fullName: "Lê Văn D",
                phoneNumber: "0987654321",
                email: "d.le@edu.vn"
            )
        )
    ]
    var body: some View {
        ZStack {
            Color.background.ignoresSafeArea()
            
            VStack {
                HomeHeader(name: "Nguyen van a", studentCode: "A44444", onOpenProfile: {
                    
                }, onOpenNotification: {
                    
                }, isProfileReady: true)
                
                LazyVStack(spacing: 15){
                    AlertList(items: demoAlerts)
                    
                    ScheduleClassList(courseClasses: demoClasses)
                }.padding(.horizontal)
                
                Spacer()
            }
        }
    }
}

#Preview {
    HomeView()
}
