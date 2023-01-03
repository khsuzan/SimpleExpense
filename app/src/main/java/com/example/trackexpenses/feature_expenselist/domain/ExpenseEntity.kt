package com.example.trackexpenses.feature_expenselist.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trackexpenses.db.ExpenseAppDb.Companion.EXPENSES_LISTS_TABLE
import kotlinx.android.parcel.Parcelize


@Entity(tableName = EXPENSES_LISTS_TABLE)
@Parcelize
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "item_title")
    val itemTitle: String,
    val amount: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable
