//
//  ProfileSummary.swift
//  Group3Project
//
//  Created by Jesse Gonzalez on 7/13/22.
//

import SwiftUI

struct ProfileSummary: View {
    var profile: Profile
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(profile.username)
                .bold()
                .font(.title)
            Text("Notifications: \(profile.prefersNotifications ? "On": "Off")")
            Text("Seasonal Photo: \(profile.seasonalPhoto.rawValue)")
        }
    }
}

struct ProfileSummary_Previews: PreviewProvider {
    static var previews: some View {
        ProfileSummary(profile: Profile.default)
    }
}

