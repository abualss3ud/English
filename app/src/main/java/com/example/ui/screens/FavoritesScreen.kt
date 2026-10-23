package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.dataset.A1VocabularyDataset
import com.example.data.model.VocabularyWord
import com.example.ui.components.WordCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.RoseError
import com.example.ui.theme.SlateBorder
import com.example.ui.viewmodel.LearningUiState

@Composable
fun FavoritesScreen(
    uiState: LearningUiState,
    onSearchChange: (String) -> Unit,
    onTopicFilterChange: (String?) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    onMarkKnown: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedWordForDialog by remember { mutableStateOf<VocabularyWord?>(null) }
    var showOnlyFavorites by remember { mutableStateOf(false) }

    val allTopics = listOf("الكل") + A1VocabularyDataset.getTopics()

    // Filter words based on search, topic, and favorite filter
    val filteredWords = uiState.allWords.filter { word ->
        val matchesSearch = uiState.searchQuery.isEmpty() ||
                word.word.contains(uiState.searchQuery, ignoreCase = true) ||
                word.arabicMeaning.contains(uiState.searchQuery, ignoreCase = true) ||
                word.englishExample.contains(uiState.searchQuery, ignoreCase = true)

        val matchesTopic = uiState.selectedTopicFilter == null ||
                uiState.selectedTopicFilter == "الكل" ||
                word.topicArabic == uiState.selectedTopicFilter

        val matchesFavorite = !showOnlyFavorites || (uiState.progressMap[word.id]?.isFavorite == true)

        matchesSearch && matchesTopic && matchesFavorite
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("favorites_screen")
    ) {
        // Search bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("search_vocab_field"),
            placeholder = { Text("ابحث بالإنجليزية أو العربية...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "بحث", tint = ElectricBlue)
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "مسح")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )

        // Topic Filter Chips & Favorites toggle
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = showOnlyFavorites,
                    onClick = { showOnlyFavorites = !showOnlyFavorites },
                    label = {
                        Text(
                            text = "المفضلة (${uiState.favoriteWords.size})",
                            fontWeight = if (showOnlyFavorites) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = if (showOnlyFavorites) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (showOnlyFavorites) RoseError else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RoseError.copy(alpha = 0.15f),
                        selectedLabelColor = RoseError
                    ),
                    modifier = Modifier.testTag("filter_fav_chip")
                )
            }

            items(allTopics) { topic ->
                val isSelected = if (topic == "الكل") uiState.selectedTopicFilter == null || uiState.selectedTopicFilter == "الكل"
                else uiState.selectedTopicFilter == topic

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        onTopicFilterChange(if (topic == "الكل") null else topic)
                    },
                    label = { Text(topic) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricBlue.copy(alpha = 0.15f),
                        selectedLabelColor = ElectricBlue
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Words Count Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${filteredWords.size} كلمة متوفرة",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "المس أي كلمة لعرض التفاصيل الكاملة",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Vocabulary List
        if (filteredWords.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (showOnlyFavorites) "لا توجد كلمات في المفضلة بعد" else "لم يتم العثور على نتائج",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (showOnlyFavorites)
                            "اضغط على أيقونة القلب في أي بطاقة لحفظها هنا للمراجعة السريعة"
                        else
                            "جرب البحث بكلمة أخرى أو تغيير تصنيف الموضوع",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredWords, key = { it.id }) { word ->
                    val isFav = uiState.progressMap[word.id]?.isFavorite == true

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { selectedWordForDialog = word }
                            .testTag("vocab_list_item_${word.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left side: English & Phonetic
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    onClick = { onSpeak(word.word) },
                                    shape = CircleShape,
                                    color = ElectricBlue.copy(alpha = 0.12f),
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.VolumeUp,
                                            contentDescription = "استمع",
                                            tint = ElectricBlue,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = word.word,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = NavyPrimary
                                    )
                                    Text(
                                        text = word.phonetic,
                                        fontSize = 12.sp,
                                        color = ElectricBlue
                                    )
                                }
                            }

                            // Right side: Arabic Meaning & Favorite Toggle
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = word.arabicMeaning,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyPrimary
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                IconButton(
                                    onClick = { onToggleFavorite(word.id) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "المفضلة",
                                        tint = if (isFav) RoseError else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Word Details Dialog
    if (selectedWordForDialog != null) {
        val word = selectedWordForDialog!!
        val isFav = uiState.progressMap[word.id]?.isFavorite == true

        AlertDialog(
            onDismissRequest = { selectedWordForDialog = null },
            confirmButton = {
                TextButton(onClick = { selectedWordForDialog = null }) {
                    Text("إغلاق", fontWeight = FontWeight.Bold)
                }
            },
            title = null,
            text = {
                WordCard(
                    word = word,
                    isFavorite = isFav,
                    onToggleFavorite = { onToggleFavorite(word.id) },
                    onSpeak = onSpeak,
                    onKnownClick = {
                        onMarkKnown(word.id, true)
                        selectedWordForDialog = null
                    },
                    onNeedReviewClick = {
                        onMarkKnown(word.id, false)
                        selectedWordForDialog = null
                    },
                    showActionButtons = true
                )
            },
            shape = RoundedCornerShape(24.dp)
        )
    }
}
