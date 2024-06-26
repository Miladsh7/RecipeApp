package com.msh.recipapp.models.home


import com.google.gson.annotations.SerializedName

data class ResponsePopular(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("results")
    val results: List<Result?>?,
    @SerializedName("totalResults")
    val totalResults: Int?
) {
    data class Result(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int?,
        @SerializedName("analyzedInstructions")
        val analyzedInstructions: List<AnalyzedInstruction?>?,
        @SerializedName("cheap")
        val cheap: Boolean?,
        @SerializedName("cookingMinutes")
        val cookingMinutes: Int?,
        @SerializedName("creditsText")
        val creditsText: String?,
        @SerializedName("cuisines")
        val cuisines: List<String?>?,
        @SerializedName("dairyFree")
        val dairyFree: Boolean?,
        @SerializedName("diets")
        val diets: List<String?>?,
        @SerializedName("dishTypes")
        val dishTypes: List<String?>?,
        @SerializedName("gaps")
        val gaps: String?,
        @SerializedName("glutenFree")
        val glutenFree: Boolean?,
        @SerializedName("healthScore")
        val healthScore: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("imageType")
        val imageType: String?,
        @SerializedName("license")
        val license: String?,
        @SerializedName("lowFodmap")
        val lowFodmap: Boolean?,
        @SerializedName("occasions")
        val occasions: List<String?>?,
        @SerializedName("preparationMinutes")
        val preparationMinutes: Int?,
        @SerializedName("pricePerServing")
        val pricePerServing: Double?,
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?,
        @SerializedName("servings")
        val servings: Int?,
        @SerializedName("sourceName")
        val sourceName: String?,
        @SerializedName("sourceUrl")
        val sourceUrl: String?,
        @SerializedName("spoonacularScore")
        val spoonacularScore: Double?,
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String?,
        @SerializedName("summary")
        val summary: String?,
        @SerializedName("sustainable")
        val sustainable: Boolean?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("vegan")
        val vegan: Boolean?,
        @SerializedName("vegetarian")
        val vegetarian: Boolean?,
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean?,
        @SerializedName("veryPopular")
        val veryPopular: Boolean?,
        @SerializedName("weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int?
    ) {
        data class AnalyzedInstruction(
            @SerializedName("name")
            val name: String?,
            @SerializedName("steps")
            val steps: List<Step?>?
        ) {
            data class Step(
                @SerializedName("equipment")
                val equipment: List<Equipment?>?,
                @SerializedName("ingredients")
                val ingredients: List<Ingredient?>?,
                @SerializedName("length")
                val length: Length?,
                @SerializedName("number")
                val number: Int?,
                @SerializedName("step")
                val step: String?
            ) {
                data class Equipment(
                    @SerializedName("id")
                    val id: Int?,
                    @SerializedName("image")
                    val image: String?,
                    @SerializedName("localizedName")
                    val localizedName: String?,
                    @SerializedName("name")
                    val name: String?
                )

                data class Ingredient(
                    @SerializedName("id")
                    val id: Int?,
                    @SerializedName("image")
                    val image: String?,
                    @SerializedName("localizedName")
                    val localizedName: String?,
                    @SerializedName("name")
                    val name: String?
                )

                data class Length(
                    @SerializedName("number")
                    val number: Int?,
                    @SerializedName("unit")
                    val unit: String?
                )
            }
        }
    }
}