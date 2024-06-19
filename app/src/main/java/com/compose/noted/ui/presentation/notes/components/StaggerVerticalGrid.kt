package com.compose.noted.ui.presentation.notes.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val columnWidth = (constraints.maxWidth / numColumns).coerceAtLeast(0)
        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        val columnHeights = IntArray(numColumns) { 0 }
        val placeables = measurables.map { measurable ->
            val column = columnHeights.withIndex().minByOrNull { it.value }?.index ?: 0
            val placeable = measurable.measure(itemConstraints)
            columnHeights[column] += placeable.height
            column to placeable
        }

        val height = columnHeights.maxOrNull()?.coerceAtLeast(constraints.minHeight) ?: constraints.minHeight

        layout(width = constraints.maxWidth, height = height) {
            val columnY = IntArray(numColumns) { 0 }

            placeables.forEach { (column, placeable) ->
                placeable.placeRelative(
                    x = columnWidth * column,
                    y = columnY[column]
                )
                columnY[column] += placeable.height
            }
        }
    }
}