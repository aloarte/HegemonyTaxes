//package com.p4r4d0x.hegemonytaxes.utils
//
//import com.aloarte.loomparesources.data.room.OompaLoompaEntity
//import com.aloarte.loomparesources.domain.model.FavoriteBo
//import com.aloarte.loomparesources.domain.model.OompaLoompaBo
//
//object TestData {
//
//    const val NAME = "Marcy"
//    const val LAST_NAME = "Karadzas"
//    const val COLOR = "red"
//    const val DESCRIPTION = "Description"
//    const val QUOTE = "quote"
//    const val FOOD = "Chocolat"
//    const val RANDOM_STRING = "random"
//    const val SONG = "song"
//    const val GENDER = "F"
//    const val IMAGE = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg"
//    const val PROFESSION = "Developer"
//    const val EMAIL = "mkaradzas1@visualengin.com"
//    const val AGE = 21
//    const val COUNTRY = "Loompalandia"
//    const val HEIGHT = 99
//    const val ID = 1
//    const val JSON_LIST = "{\n" +
//            "    \"current\": 1,\n" +
//            "    \"total\": 2,\n" +
//            "    \"results\": [\n" +
//            "        {\n" +
//            "            \"first_name\": \"$NAME\",\n" +
//            "            \"last_name\": \"$LAST_NAME\",\n" +
//            "            \"favorite\": {\n" +
//            "                \"color\": \"$COLOR\",\n" +
//            "                \"food\": \"$FOOD\",\n" +
//            "                \"random_string\": \"$RANDOM_STRING\",\n" +
//            "                \"song\": \"$SONG\"\n" +
//            "            },\n" +
//            "            \"gender\": \"$GENDER\",\n" +
//            "            \"image\": \"$IMAGE\",\n" +
//            "            \"profession\": \"$PROFESSION\",\n" +
//            "            \"email\": \"$EMAIL\",\n" +
//            "            \"age\": $AGE,\n" +
//            "            \"country\": \"$COUNTRY\",\n" +
//            "            \"height\": $HEIGHT,\n" +
//            "            \"id\": $ID\n" +
//            "        }]\n" +
//            "}"
//    const val JSON_DETAIL = "{\n" +
//            "    \"last_name\": \"$LAST_NAME\",\n" +
//            "    \"description\": \"$DESCRIPTION\",\n" +
//            "    \"image\": \"$IMAGE\",\n" +
//            "    \"profession\": \"$PROFESSION\",\n" +
//            "    \"quota\": \"$QUOTE\",\n" +
//            "    \"height\": $HEIGHT,\n" +
//            "    \"first_name\": \"$NAME\",\n" +
//            "    \"country\": \"$COUNTRY\",\n" +
//            "    \"age\": $AGE,\n" +
//            "    \"favorite\": {\n" +
//            "        \"color\": \"$COLOR\",\n" +
//            "        \"food\": \"$FOOD\",\n" +
//            "        \"random_string\": \"$RANDOM_STRING\",\n" +
//            "        \"song\": \"$SONG\"\n" +
//            "    },\n" +
//            "    \"gender\": \"$GENDER\",\n" +
//            "    \"email\": \"$EMAIL\"\n" +
//            "}"
//
//    val oompaLoompa = OompaLoompaBo(
//        firstName = NAME,
//        lastName = LAST_NAME,
//        profession = PROFESSION,
//        image = IMAGE,
//        id = ID,
//        height = HEIGHT,
//        gender = GENDER,
//        country = COUNTRY,
//        age = AGE,
//        email = EMAIL,
//        favorite = FavoriteBo(
//            song = SONG,
//            color = COLOR,
//            randomString = RANDOM_STRING,
//            food = FOOD
//        )
//    )
//    val detailedOompaLoompa = OompaLoompaBo(
//        firstName = NAME,
//        lastName = LAST_NAME,
//        description = DESCRIPTION,
//        quote = QUOTE,
//        profession = PROFESSION,
//        image = IMAGE,
//        id = ID,
//        height = HEIGHT,
//        gender = GENDER,
//        country = COUNTRY,
//        age = AGE,
//        email = EMAIL,
//        favorite = FavoriteBo(
//            song = SONG,
//            color = COLOR,
//            randomString = RANDOM_STRING,
//            food = FOOD
//        )
//    )
//
//    val oompaLoompaEntity = OompaLoompaEntity(
//        firstName = NAME,
//        lastName = LAST_NAME,
//        description = null,
//        quote = null,
//        profession = PROFESSION,
//        image = IMAGE,
//        id = 0,
//        height = HEIGHT,
//        gender = GENDER,
//        country = COUNTRY,
//        age = AGE,
//        email = EMAIL,
//        song = SONG,
//        color = COLOR,
//        randomString = RANDOM_STRING,
//        food = FOOD
//
//    )
//
//    val detailedOompaLoompaEntity = OompaLoompaEntity(
//        firstName = NAME,
//        lastName = LAST_NAME,
//        description = DESCRIPTION,
//        quote = QUOTE,
//        profession = PROFESSION,
//        image = IMAGE,
//        id = ID,
//        height = HEIGHT,
//        gender = GENDER,
//        country = COUNTRY,
//        age = AGE,
//        email = EMAIL,
//        song = SONG,
//        color = COLOR,
//        randomString = RANDOM_STRING,
//        food = FOOD
//    )
//
//}