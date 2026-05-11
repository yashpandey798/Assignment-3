"""
main.py
Entry point — detects OS type and instantiates the correct class.
"""

import platform
import sys


def main():
    os_type = platform.system()  # Returns 'Windows' or 'Linux' or 'Darwin'

    print("=======================================================")
    print(" Hardware Info Fetcher")
    print(" Detected OS : " + os_type)
    print("=======================================================")

    if os_type == "Windows":
        from windows_host import WindowsHost
        host = WindowsHost()

    elif os_type == "Linux":
        from linux_host import LinuxHost
        host = LinuxHost()

    else:
        print("ERROR: Unsupported OS: " + os_type)
        print("This program supports Windows and Linux only.")
        sys.exit(1)

    # Fetch hardware info
    host.get_hardware_info()

    # Display in JSON format
    print("\nHardware Information:")
    host.display_hardware_info()
    print("=======================================================")


if __name__ == "__main__":
    main()
