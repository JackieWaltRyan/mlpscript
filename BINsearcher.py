from binascii import unhexlify
from os import listdir, makedirs
from os.path import exists, isfile, join
from re import findall
from typing import List


def error(mes, file=None):
    if mes == 1:
        print(f"Файл \"{file}\" отсутсвует или в нем нет ключевых слов. Добавьте ключевые слова в этот файл. На одной "
              f"строке одно слово!")
    if mes == 2:
        print("Папки \"bin\" не существует или в ней нет файлов! Создайте папку \"bin\" и загрузите в нее бинарные "
              "файлы в которых нужно найти данные!")
    input()
    exit()


def load_keywords(file: str) -> List[str]:
    data_list: List[str] = []
    if exists(file):
        with open(file, "r", encoding="utf-8") as f:
            for line in f.readlines():
                if line.strip() != "":
                    data_list.append(line.strip())
        if len(data_list) == 0:
            error(1, file)
        else:
            return data_list
    else:
        with open("keywords.txt", "a+", encoding="utf-8") as keywords:
            keywords.close()
        error(1, file)


def get_decoded_data(file: str, found_data: List[str]) -> List[str]:
    found_data_list: List[str] = []
    with open(file, "rb") as opened_file:
        for raw_data in opened_file.readlines():
            decoded_data = unhexlify(raw_data.hex()).decode("ANSI")
            for keyword in found_data:
                for tag in findall(f"({keyword}_[a-zA-Z0-9_]+)", decoded_data):
                    found_data_list.append(tag)
    return found_data_list


def check_dir(path: str) -> bool:
    if exists(path):
        files: List[str] = [x for x in listdir(path) if isfile(join(path, x))]
        if len(files) == 0:
            return False
        else:
            return True
    else:
        makedirs("bin")
        return False


if __name__ == "__main__":
    keywords_list: List[str] = load_keywords("keywords.txt")
    datafiles_path: str = "bin/"
    if check_dir(datafiles_path):
        for bin_file in listdir(datafiles_path):
            print(f"Ищем данные в: {join(datafiles_path, bin_file)}")
            data: List[str] = get_decoded_data(join(datafiles_path, bin_file), keywords_list)
            with open("output_data.txt", "a+", encoding="utf-8") as output_file:
                for i in set(data):
                    output_file.write(f"{i}\n")
    else:
        error(2)
