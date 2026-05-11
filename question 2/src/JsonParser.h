#ifndef JSON_PARSER_H
#define JSON_PARSER_H

#include "InventoryItem.h"
#include <vector>
#include <string>

/**
 * Parses the inventory JSON file and returns list of InventoryItem objects.
 * Uses manual parsing (no external library needed).
 */
class JsonParser {
public:
    std::vector<InventoryItem> parse(const std::string& filePath) const;

private:
    std::string extractValue(const std::string& block, const std::string& key) const;
};

#endif
