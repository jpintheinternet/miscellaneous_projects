//
//  Group3ProjectApp.swift
//  Group3Project
//
//  Created by Jesse Gonzalez on 6/10/22.
//

import SwiftUI

@main
struct Group3ProjectApp: App {
    @StateObject private var modelData = ModelData()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(modelData)
        }
    }
}
