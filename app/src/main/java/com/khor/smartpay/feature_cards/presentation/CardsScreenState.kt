package com.khor.smartpay.feature_cards.presentation

import com.khor.smartpay.feature_auth.domain.model.Card

data class CardsScreenState(
    val cards: List<Card> = emptyList()
)