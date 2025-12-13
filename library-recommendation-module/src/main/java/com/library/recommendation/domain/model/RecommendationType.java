package com.library.recommendation.domain.model;

/**
 * Recommendation type enumeration
 */
public enum RecommendationType {
    COLLABORATIVE_FILTERING,  // Based on similar users' preferences
    CONTENT_BASED,           // Based on book content/attributes
    POPULAR,                 // Based on overall popularity
    TRENDING,                // Based on recent trends
    PERSONALIZED             // Hybrid approach
}
