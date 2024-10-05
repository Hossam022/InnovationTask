package com.example.common.extensions

fun String.getYearFromDate() = this.split("-").firstOrNull()