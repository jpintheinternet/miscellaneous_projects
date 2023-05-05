//
//  ProfileEditor.swift
//  Group3Project
//
//  Created by Jesse Gonzalez on 7/25/22.
//

import SwiftUI

struct ProfileEditor: View {
    @Binding var profile: Profile
    
    var body: some View {
        List{
            HStack{
                Text("User Name: ")
                    .bold()
                Divider()
                TextField("User Name: ", text: $profile.username)
            
            }
            
            Toggle(isOn: $profile.prefersNotifications){
                Text("Enable Notification")
                    .bold()
            }
            
            VStack{
                Text("Seasonal Photo")
                    .bold()
                Picker("Seasonal Photo", selection: $profile.seasonalPhoto)
                {
                    ForEach(Profile.Season.allCases) {season in
                        Text(season.rawValue).tag(season)
                        
                    }
                }
                .pickerStyle(.segmented)
            }
            
            DatePicker(selection: $profile.updateDate, displayedComponents: .date)
            {
                Text("Last Update Date")
                    .bold()
            }
        }
    }
}

struct ProfileEditor_Previews: PreviewProvider {
    static var previews: some View {
        ProfileEditor(profile: .constant(.default))
    }
}
