//
//  Book.swift
//  Group3Project
//
//  Created by Jesse Gonzalez on 6/17/22.
//



import Foundation
import SwiftUI

struct Book: Hashable, Codable, Identifiable {
    var id: Int
    var name: String
    var author: String
    var price: Double
    var description: String
    var isFavorite: Bool
    var isFeatured: Bool
    
    var category: Category
    enum Category: String, CaseIterable, Codable {
       case science = "Science"
       case math = "Math"
       case novel = "Novel"
    }
    
    private var imageName: String
    var image: Image {
        Image(imageName)
    }
    
    private var backgroundName: String
    var backgroundImage: Image {
        Image(backgroundName)
    }
    
}
