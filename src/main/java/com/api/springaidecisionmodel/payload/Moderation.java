package com.api.springaidecisionmodel.payload;

public record Moderation(boolean spam, String topic, String tone) {
}
