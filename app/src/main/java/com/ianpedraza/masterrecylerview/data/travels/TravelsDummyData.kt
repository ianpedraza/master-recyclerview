package com.ianpedraza.masterrecylerview.data.travels

import java.util.UUID

object TravelsDummyData {
    val covers = arrayOf(
        TravelsCover(
            coverId = UUID.randomUUID().toString(),
            title = "Bora Bora",
            headline = "Monsoon 2020 - 10 Days",
            image = "https://cdn.pixabay.com/photo/2017/12/16/22/22/bora-bora-3023437_1280.jpg"
        ),
        TravelsCover(
            coverId = UUID.randomUUID().toString(),
            title = "Croatia",
            headline = "Summer 2020 - 20 Days",
            image = "https://travellersworldwide.com/wp-content/uploads/2022/05/shutterstock_1298566075-1536x960.jpg.webp"
        ),
        TravelsCover(
            coverId = UUID.randomUUID().toString(),
            title = "Cancun",
            headline = "Summer 2020 - 15 Days",
            image = "https://i0.wp.com/blog.vivaaerobus.com/wp-content/uploads/2019/12/Mejores-Playas-de-Cancún.jpg"
        ),
        TravelsCover(
            coverId = UUID.randomUUID().toString(),
            title = "Guam",
            headline = "Spring 2022 - 15 Days",
            image = "https://a.cdn-hotels.com/gdcs/production49/d1519/6f89ae5d-542c-4fee-b333-a35761fe33d1.jpg"
        )
    )

    val descriptions = arrayOf(
        TravelsDescription(
            descriptionId = UUID.randomUUID().toString(),
            title = "Bali, Indonesia",
            body = "You'll find beaches, volcanoes, Komodo dragons and jungles sheltering elephants, orangutans and tigers. Basically its paradise"
        ),
        TravelsDescription(
            descriptionId = UUID.randomUUID().toString(),
            title = "Kerry, Ireland",
            body = "All the way west in Ireland is one or the country's most scenic countries. Kerry's mountains."
        ),
        TravelsDescription(
            descriptionId = UUID.randomUUID().toString(),
            title = "Huatulco, Mexico",
            body = "Centered on the town of La Crucecita, is a tourist development in Mexico. It is located on the Pacific coast in the state of Oaxaca. Huatulco's tourism industry is centered on its nine bays, thus the name Bahias de Huatulco, but has since been unofficially shortened to simply Huatulco."
        ),
        TravelsDescription(
            descriptionId = UUID.randomUUID().toString(),
            title = "Barcelona, Spain",
            body = "City on the coast of northeastern Spain. It is the capital and largest city of the autonomous community of Catalonia, as well as the second most populous municipality of Spain."
        ),
        TravelsDescription(
            descriptionId = UUID.randomUUID().toString(),
            title = "London, United Kingdom",
            body = "London, the capital of England and the United Kingdom, is a 21st-century city with history stretching back to Roman times."
        )
    )

    val ads = arrayOf(
        Ad(
            addId = UUID.randomUUID().toString(),
            title = "Ad: Christmas Holiday",
            body = "70% OFF on christmas sale"
        ),
        Ad(
            addId = UUID.randomUUID().toString(),
            title = "Ad: Summer Offer",
            body = "60% OFF on summer sale"
        ),
        Ad(
            addId = UUID.randomUUID().toString(),
            title = "Ad: Spring Holiday",
            body = "40% OFF on spring sale"
        )
    )
}
