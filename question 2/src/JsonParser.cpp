#include "JsonParser.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <stdexcept>

/**
 * Extracts a string value for a given key from a JSON block.
 * e.g. extractValue(block, "os") returns "Windows"
 */
std::string JsonParser::extractValue(const std::string& block, const std::string& key) const {
    // Search for "key" : "value"
    std::string searchKey = "\"" + key + "\"";
    size_t keyPos = block.find(searchKey);
    if (keyPos == std::string::npos) return "";

    // Find the colon after key
    size_t colonPos = block.find(":", keyPos);
    if (colonPos == std::string::npos) return "";

    // Find opening quote of value
    size_t openQuote = block.find("\"", colonPos);
    if (openQuote == std::string::npos) return "";

    // Find closing quote of value
    size_t closeQuote = block.find("\"", openQuote + 1);
    if (closeQuote == std::string::npos) return "";

    return block.substr(openQuote + 1, closeQuote - openQuote - 1);
}

/**
 * Parses the full JSON file and returns list of InventoryItem
 */
std::vector<InventoryItem> JsonParser::parse(const std::string& filePath) const {
    std::ifstream file(filePath);
    if (!file.is_open()) {
        throw std::runtime_error("Cannot open file: " + filePath);
    }

    // Read entire file into string
    std::stringstream buffer;
    buffer << file.rdbuf();
    std::string content = buffer.str();

    std::vector<InventoryItem> items;

    // Find "inventory" block
    size_t inventoryStart = content.find("\"inventory\"");
    if (inventoryStart == std::string::npos) {
        throw std::runtime_error("Invalid JSON: 'inventory' key not found");
    }

    // Find each IP entry block by looking for IP pattern
    size_t pos = inventoryStart;
    while (true) {
        // Find next opening brace for an item (after the IP key)
        size_t ipStart = content.find("\"10.", pos);
        if (ipStart == std::string::npos) break;

        // Find the opening brace of this item's data block
        size_t blockStart = content.find("{", ipStart);
        if (blockStart == std::string::npos) break;

        // Find the closing brace of this item's data block
        size_t blockEnd = content.find("}", blockStart);
        if (blockEnd == std::string::npos) break;

        std::string block = content.substr(blockStart, blockEnd - blockStart + 1);

        InventoryItem item;
        item.ip     = extractValue(block, "ip");
        item.os     = extractValue(block, "os");
        item.memory = extractValue(block, "memory");
        item.cpu    = extractValue(block, "cpu");
        item.disk   = extractValue(block, "disk");

        if (!item.ip.empty()) {
            items.push_back(item);
        }

        pos = blockEnd + 1;
    }

    return items;
}
