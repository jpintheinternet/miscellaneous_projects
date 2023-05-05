//
//  CategoryHome.swift
//  Group3Project
//
//  Created by Jesse Gonzalez on 7/7/22.
//

import SwiftUI

struct CategoryHome: View {
    @EnvironmentObject var modelData: ModelData
    
    @State private var showingProfile = false
    var body: some View {
        NavigationView {
            List{
                NavigationLink{
                    BookDetail(book: modelData.features[0])
                } label: {
                    modelData.features[0].image
                    .resizable()
                    .scaledToFit()
                    .frame( height: 200)
                    .clipped()
                }
                .listRowInsets(EdgeInsets())
                
//                modelData.features[0].image
//                    .resizable()
//                    .scaledToFit()
//                    .frame( height: 200)
//                    .clipped()
//                    .listRowInsets(EdgeInsets())
                
                ForEach(modelData.categories.keys.sorted(), id: \.self) {key in
                    CategoryRow(categoryName: key, items: modelData.categories[key]!)
                }
                .listRowInsets(EdgeInsets())
            }
            .listStyle(.inset)
            .navigationTitle("Featured")
            .toolbar{
                Button{
                    showingProfile.toggle()
                } label: {
                    Label("User Profile", systemImage: "person.crop.circle")
                }
            }
            .sheet(isPresented: $showingProfile){
                ProfileHost()
            }
        }
    }
}

struct CategoryHome_Previews: PreviewProvider {
    static var previews: some View {
        CategoryHome()
            .environmentObject(ModelData())
    }
}
