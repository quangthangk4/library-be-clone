package com.library.recommendation.domain.entity;

/**
 * Recommendation type enumeration
 */
public enum RecommendationType {
    /**
     * Based on user's borrowing history
     */
    BASED_ON_HISTORY,

    /**
     * Based on similar books
     */
    SIMILAR_BOOKS,

    /**
     * Popular books (trending)
     */
    POPULAR,

    /**
     * Based on user's ratings and reviews
     */
    PERSONALIZED,

    /**
     * Books from same author
     */
    SAME_AUTHOR,

    /**
     * Books from same category
     */
    SAME_CATEGORY,

    /**
     * Editor's pick
     */
    EDITORIAL
}
