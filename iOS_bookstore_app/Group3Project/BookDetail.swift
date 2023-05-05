//
//  BookDetail.swift
//  Group3Project
//
//  Created by Jesse Gonzalez on 6/28/22.
//

import SwiftUI

struct BookDetail: View {
    @EnvironmentObject var modelData: ModelData
    var book: Book
    
    var bookIndex: Int{
        modelData.books.firstIndex(where: {$0.id == book.id})!
    }
    
    var body: some View {
        ScrollView{
            
            BookView(image: book.backgroundImage)
                .ignoresSafeArea(edges: .top)
                .frame(height: 300)
            
            CircleImage(image: book.image)
                .offset(y: -130)
                .padding(.bottom, -130)
                
            VStack(alignment: .leading) {
                
                HStack{
                Text(book.name)
                    .font(.title)
                    FavoriteButton(isSet: $modelData.books[bookIndex].isFavorite)
                }
                
                HStack {
                    Text(book.author)
                   Spacer()
                    Text("$" + String(book.price))
                        .foregroundColor(.blue)
                }
                .font(.subheadline)
                .foregroundColor(.secondary)
                
                Divider()
                    .foregroundColor(.blue)
                
                Text("About \(book.name)")
                    .font(.title2)
                Text(book.description)
                    .foregroundColor(.secondary)
                
            }
            .padding()
            
            Spacer()
            
         }
        .navigationTitle(book.name)
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct BookDetail_Previews: PreviewProvider {
    static let modelData = ModelData()
    
    static var previews: some View {
        BookDetail(book: modelData.books[5])
            .environmentObject(modelData)
    }
}
