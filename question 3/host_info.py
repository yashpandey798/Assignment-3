"""
host_info.py
Parent class HostInfo — contains common attributes and abstract method.
"""

from abc import ABC, abstractmethod
import json


class HostInfo(ABC):
    """
    Abstract base class for host hardware information.
    LinuxHost and WindowsHost inherit from this class.
    """

    def __init__(self):
        self.hostname  = None
        self.memory    = None
        self.cpu       = None
        self.ip        = None
        self.disk_size = None

    @abstractmethod
    def get_hardware_info(self):
        """
        Abstract method — must be implemented by subclasses.
        Queries hardware info using OS level commands
        and updates hostname, memory, cpu, ip, disk_size attributes.
        """
        pass

    def display_hardware_info(self):
        """
        Displays all hardware info in JSON format.
        """
        info = {
            "hostname":  self.hostname,
            "memory":    self.memory,
            "cpu":       self.cpu,
            "ip":        self.ip,
            "disk_size": self.disk_size
        }
        print(json.dumps(info, indent=4))
