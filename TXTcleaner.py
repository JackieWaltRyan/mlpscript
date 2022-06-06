from os import getcwd, walk, remove
from os.path import join

if __name__ == "__main__":
    path = getcwd()
    for root, dirs, files in walk(path):
        for file in files:
            if not file.endswith("english.txt") and not file.endswith("russian.txt") and not file.endswith(".exe"):
                filename = join(root, file)
                print(f"Удаляем: {filename}")
                remove(filename)
