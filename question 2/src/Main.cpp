#include <iostream>
#include <string>
#include "JsonParser.h"
#include "InventoryFilter.h"

/**
 * Main entry point for Inventory Filter program.
 *
 * Usage:
 *   ./InventoryFilter <jsonFilePath> <filterCriteria>
 *
 * Examples:
 *   ./InventoryFilter resources/inventory.json Memory
 *   ./InventoryFilter resources/inventory.json CPU
 *   ./InventoryFilter resources/inventory.json Linux
 *   ./InventoryFilter resources/inventory.json Windows
 */
int main(int argc, char* argv[]) {

    // Check arguments
    if (argc < 3) {
        if (argc < 2) {
            throw MissingFilterCriteriaException();
        }
        throw MissingFilterCriteriaException();
    }

    std::string filePath  = argv[1];
    std::string criteria  = argv[2];

    try {
        // Parse JSON
        JsonParser parser;
        std::vector<InventoryItem> items = parser.parse(filePath);

        if (items.empty()) {
            std::cout << "No inventory items found in file." << std::endl;
            return 1;
        }

        // Apply filter
        InventoryFilter filter;
        filter.applyFilter(items, criteria);

    } catch (const MissingFilterCriteriaException& e) {
        std::cerr << e.what() << std::endl;
        std::cerr << "Usage: ./InventoryFilter <jsonFilePath> <Memory|CPU|Linux|Windows>" << std::endl;
        return 1;
    } catch (const InvalidFilterCriteriaException& e) {
        std::cerr << e.what() << std::endl;
        return 1;
    } catch (const std::exception& e) {
        std::cerr << "ERROR: " << e.what() << std::endl;
        return 1;
    }

    return 0;
}
