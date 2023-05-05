//
//  ModelData.swift
//  Group3Project
//
//  Created by Jesse Gonzalez on 6/17/22.
//

import Foundation


final class ModelData: ObservableObject {
    @Published var books: [Book] = load("bookData.json")
    
    @Published var profile = Profile.default
    
    var features: [Book]{
        books.filter{ $0.isFeatured}
    }
    
    var categories: [String: [Book]]{
        Dictionary(
            grouping: books, by: { $0.category.rawValue }
        )
    }
}



func load<T: Decodable>(_ filename: String) -> T {
    let data: Data
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
    else {
        fatalError("Can't find file")
    }
    
        do {
            data = try Data(contentsOf: file)
        } catch {
            fatalError("Can't read the file")
        }
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Can't convert to Json")
    }
}

