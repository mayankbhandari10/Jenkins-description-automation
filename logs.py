# my_script.py
import re
import sys


def main(build_log):
    # Define the pattern to search for
    pattern = r"Error: (.+)"

    # Search for the pattern in the build log
    match = re.search(pattern, build_log)

    # Return the matched error message if found, or a default message
    if match:
        return match.group(1)
    else:
        return "No error message found in the build log."


if __name__ == "__main__":
    # Read the build log from the command-line argument
    build_log = sys.argv[1]

    # Call the main function and print the result
    result = main(build_log)
    print(result)
