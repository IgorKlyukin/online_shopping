CREATE OR REPLACE FUNCTION public.get_final_categories_from_parent(
	parentid bigint)
    RETURNS TABLE(id bigint, name character varying)
    LANGUAGE 'sql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
WITH RECURSIVE Recursii(children_id, parent_id)
                   AS
                   (
                       SELECT children_id, parent_id FROM public.t_category_parent
                       WHERE parent_id = parentId
                       UNION ALL
                       SELECT e.children_id, e.parent_id FROM public.t_category_parent e
                                                                  JOIN Recursii r ON e.parent_id = r.children_id
                   )

select category0_.id as id, category0_.name as name
from t_category category0_
         left outer join t_category_parent parent1_ on category0_.id=parent1_.parent_id
         left outer join t_category category2_ on parent1_.children_id=category2_.id
where category2_.id is null
  and category0_.id in
      (SELECT id FROM public.t_category
       WHERE id in (SELECT children_id FROM Recursii)
       UNION
       SELECT id FROM public.t_category
       WHERE id = parentId)
order by category0_.id asc
$BODY$;

ALTER FUNCTION public.get_final_categories_from_parent(bigint)
    OWNER TO postgres;

INSERT INTO public.t_category (id, name) VALUES (1, 'Овощи, фрукты, ягоды');
INSERT INTO public.t_category (id, name) VALUES (2, 'Овощи');
INSERT INTO public.t_category (id, name) VALUES (3, 'Огурцы');
INSERT INTO public.t_category (id, name) VALUES (4, 'Помидоры');
INSERT INTO public.t_category (id, name) VALUES (5, 'Фрукты');
INSERT INTO public.t_category (id, name) VALUES (6, 'Апельсин');
INSERT INTO public.t_category (id, name) VALUES (7, 'Яблоко');
INSERT INTO public.t_category (id, name) VALUES (8, 'Ягоды');
INSERT INTO public.t_category (id, name) VALUES (9, 'Вишня');
INSERT INTO public.t_category (id, name) VALUES (10, 'Арбуз');
INSERT INTO public.t_category (id, name) VALUES (11, 'Рыба, икра');
INSERT INTO public.t_category (id, name) VALUES (12, 'Рыба');
INSERT INTO public.t_category (id, name) VALUES (13, 'Треска');
INSERT INTO public.t_category (id, name) VALUES (14, 'Камбала');
INSERT INTO public.t_category (id, name) VALUES (15, 'Икра');
INSERT INTO public.t_category (id, name) VALUES (16, 'Чёрная');
INSERT INTO public.t_category (id, name) VALUES (17, 'Красная');
INSERT INTO public.t_category (id, name) VALUES (18, 'Птица, мясо, деликатесы');
INSERT INTO public.t_category (id, name) VALUES (19, 'Птица');
INSERT INTO public.t_category (id, name) VALUES (20, 'Курица');
INSERT INTO public.t_category (id, name) VALUES (21, 'Индейка');
INSERT INTO public.t_category (id, name) VALUES (22, 'Мясо');
INSERT INTO public.t_category (id, name) VALUES (23, 'Свинина');
INSERT INTO public.t_category (id, name) VALUES (24, 'Говядина');
INSERT INTO public.t_category (id, name) VALUES (25, 'Деликатесы');
INSERT INTO public.t_category (id, name) VALUES (26, 'Сосиски');
INSERT INTO public.t_category (id, name) VALUES (27, 'Колбасы');
INSERT INTO public.t_category (id, name) VALUES (28, 'Молоко, сыр, яйца');
INSERT INTO public.t_category (id, name) VALUES (29, 'Молоко');
INSERT INTO public.t_category (id, name) VALUES (30, 'Молочко');
INSERT INTO public.t_category (id, name) VALUES (31, 'Сливки');
INSERT INTO public.t_category (id, name) VALUES (32, 'Сыр');
INSERT INTO public.t_category (id, name) VALUES (33, 'Российский');
INSERT INTO public.t_category (id, name) VALUES (34, 'Голландский');
INSERT INTO public.t_category (id, name) VALUES (35, 'Яйца');
INSERT INTO public.t_category (id, name) VALUES (36, 'Куриные');
INSERT INTO public.t_category (id, name) VALUES (37, 'Перепелиные');
INSERT INTO public.t_category (id, name) VALUES (38, 'Воды, соки, напитки');
INSERT INTO public.t_category (id, name) VALUES (39, 'Воды');
INSERT INTO public.t_category (id, name) VALUES (40, 'Артезианская');
INSERT INTO public.t_category (id, name) VALUES (41, 'Минеральная');
INSERT INTO public.t_category (id, name) VALUES (42, 'Соки');
INSERT INTO public.t_category (id, name) VALUES (43, 'Свежевыжатые');
INSERT INTO public.t_category (id, name) VALUES (44, 'Нектар');
INSERT INTO public.t_category (id, name) VALUES (45, 'Напитки');
INSERT INTO public.t_category (id, name) VALUES (46, 'Безалкогольные');
INSERT INTO public.t_category (id, name) VALUES (47, 'Газировка');

INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (2, 1);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (3, 2);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (4, 2);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (5, 1);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (6, 5);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (7, 5);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (8, 1);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (9, 8);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (10, 8);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (12, 11);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (13, 12);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (14, 12);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (15, 11);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (16, 15);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (17, 15);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (19, 18);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (20, 19);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (21, 19);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (22, 18);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (23, 22);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (24, 22);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (25, 18);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (26, 25);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (27, 25);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (29, 28);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (30, 29);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (31, 29);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (32, 28);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (33, 32);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (34, 32);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (35, 28);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (36, 35);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (37, 35);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (39, 38);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (40, 39);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (41, 39);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (42, 38);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (43, 42);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (44, 42);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (45, 38);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (46, 45);
INSERT INTO public.t_category_parent (children_id, parent_id) VALUES (47, 45);

INSERT INTO public.t_product (id, description, name, price) VALUES (1, 'Кусочек нежнейшего мясо по не адекватной цене, только для вас!', 'Треска Borealis', 699.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (2, 'Треска Agama филе лойн свежемороженное 400г - 100% натуральный.', 'Треска Agama', 599.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (3, 'Камбала Polar филе замороженное 400г - мороженные рыбные болты', 'Камбала Polar', 239.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (4, 'Вкус ваших рецепторов поистине уникален, попробовав наш продукт, не забудьте сходить в туалет', 'Камбала Вкус Арт', 399.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (5, 'Русский икорный дом Стандарт зернистая 100г', 'Икра осетровая Стандарт', 5699.00);
INSERT INTO public.t_product (id, description, name, price) VALUES (6, 'Чёрные жемчущины России 56.8г', 'Икра осетровая Caviar', 3299.00);
INSERT INTO public.t_product (id, description, name, price) VALUES (7, 'Икра форели 230г - не только вкусный, но и полезный продукт.', 'Икра лососевая', 1800.00);
INSERT INTO public.t_product (id, description, name, price) VALUES (8, 'Довольно редкий вид икры, настоящий деликатес', 'Икра Нерка Путина лососевая', 859.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (9, 'Это натуральный продукт с высоким содержанием курицы', 'Цыпленок Петелинка тушка', 199.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (10, 'Охлажденные части тушек цыплят, выращенных с ГМО', 'Филе куриное Наша птичка', 206.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (11, 'Аппетитный шницель из жопки индейки', 'Шницель Индилайт', 133.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (12, '100% натуральный полуфабрикат, со строгим контролем.', 'Филе Пава-Пава из бедра', 231.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (13, 'Это кусок тазобедренной части свиной туши', 'Окорок задний свиной', 269.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (14, 'Это кусок шейного отруба свиной туши', 'Шейка свиная без кости', 369.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (15, 'Это часть голяшки, из которой получается самый вкусный и ароматный мясной бульон', 'Мясо для бульона говяжье', 499.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (16, 'Это охлажденный полуфабрикат из говядины мясных пород.', 'Шейная часть говядины без кости', 538.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (17, 'Вареные колбасные изделия, изготовленные в соответствии с ГОСТ 200г', 'Сосиски Мираторг Молочные', 96.80);
INSERT INTO public.t_product (id, description, name, price) VALUES (18, 'Как молочные, но с курицей 350г', 'Сосиски Мираторг Баварские', 131.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (19, 'Колбасное изделие, изготовленное в соответствии с рецептурой ГОСТ 430г', 'Колбаса Мираторг Краковская', 239.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (20, 'Один из самых популярных продуктов бренда.', 'Колбаса Ближние горки Кремлёвская', 1179.00);
INSERT INTO public.t_product (id, description, name, price) VALUES (21, 'Это чистая природная вода из заповедных мест России.', 'Вода Святой Источник питьевая 330мл', 10.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (22, 'Это чистая кондиционированная вода первой категории.', 'Вода Bonaqua питьевая 2л', 39.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (23, 'Минеральная лечебно-столовая газированная 500мл', 'Вода Боржоми', 49.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (24, 'Минеральная лечебно-столовая 1л', 'Вода Кисловодская Целебная', 96.80);
INSERT INTO public.t_product (id, description, name, price) VALUES (25, 'Это оригинальный апельсиновый купаж', 'Сок Rich Апельсин 1л', 156.75);
INSERT INTO public.t_product (id, description, name, price) VALUES (26, 'Хороший качественный сок.', 'Сок Swell Яблочный 750мл', 158.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (27, 'Все соответствует составу', 'Нектар J-7 Тонус Энергия Цитрусовый микс', 99.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (28, 'Вкусный с мякотью', 'Нектар Я Манго 970мл', 89.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (29, 'Вставляет только после десяти ящиков!', 'Пиво Benediktiner Weissbier 0.5% 0.5л', 153.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (30, 'Хоть и безалкогольное, но алкаши не покупают', 'Пиво Bakalar светлое 0.5л', 148.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (31, 'Легендарный американский напиток!', 'Напиток Coca-Cola 2л', 94.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (32, 'Пародия на легендарный американский напиток!', 'Напиток Pepsi 2л', 89.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (33, 'Линейка свежих продуктов, созданная в партнерстве с сетью "ВкусВилл"', 'Молоко Маркет Зеленая линия 3.2% 1л', 46.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (34, 'Отличное молоко. По акции - еще лучше)', 'Молоко Село Зеленое 3.2% 1.947л', 153.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (35, 'Нежные сливки, изготовленые из отборного молока.', 'Сливки Петмол 33% 500мл', 199.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (36, 'Удобная упаковка, нормальные сливки для кофе', 'Сливки Parmalat 11% 200мл', 59.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (37, 'Вкусный сыр. Едим с удовольствием. Детям не нравится. Спасибо за скидку!', 'Сыр Брест-Литовск Российский 50% 200г', 174.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (38, 'Пастеризованное молоко, соль, кислоты, тараканы, грибы', 'Сыр Зеленая линия Российский молодой 50%', 599.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (40, 'Вкусный! По вкусу чем то напоминает Маасам.', 'Сыр Valio Голландский 45% 120г', 172.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (39, 'Производитель не несёт ответственность за испорченный продукт.', 'Сыр Зелёная линия Голландский 45%', 627.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (41, '100% натуральный высококачественный продукт отборной категории.', 'Яйца Волжанин Омега-3 С0 6шт', 49.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (42, 'The best protein!', 'Белок яичный Волжанин 500г', 84.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (43, 'Одни из лучших в своём классе.', 'Яйца перепелиные', 79.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (44, 'Для тех кто не видит разницы, пусть платит больше', 'Премиум яйца перепелиные', 189.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (46, 'Особый сорт с высокими вкусовыми качествами', 'Огурцы Бакинские 300г', 189.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (47, 'Гибридная разновидность томатов', 'Помидоры Черри красные 250г', 149.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (48, 'Для тех кто не понимает, почему всё так дёшево.', 'Помидоры Пинк Парадайз 400г', 499.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (45, 'Сорт небольшого размера с тонкой колючей кожурой', 'Огурцы короткоплодные', 169.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (49, 'Особый сорт достаточно крупного размера', 'Апельсины Навелин', 109.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (50, 'Цитрусовый сорт Sanguinelli, выведенные в 1929 году в Испании', 'Апельсины красные', 389.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (51, 'Выведенные в Австралии. У них ярко-красная кожура.', 'Яблоки Кримсон Сноу', 169.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (52, 'Выведенному в 1979 году австралийскими селекционерами путем скрещивания сортов Голден Делишес и Леди Вильям.', 'Яблоки Пинк Леди 700г', 159.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (53, 'Сладкая ягода только для вас!', 'Вишня 250г', 229.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (54, 'Выведенная в соседской деревне, какой-то бабкой', 'Вишня', 499.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (55, 'Отличается относительно небольшим размером', 'Арбуз Premium', 329.90);
INSERT INTO public.t_product (id, description, name, price) VALUES (56, 'Классический астраханский арбуз с косточками', 'Арбуз отечественный', 49.90);

INSERT INTO public.t_category_products (categories_id, products_id) VALUES (13, 1);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (13, 2);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (14, 3);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (14, 4);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (16, 5);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (16, 6);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (17, 7);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (17, 8);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (20, 9);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (20, 10);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (21, 11);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (21, 12);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (23, 13);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (23, 14);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (24, 15);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (24, 16);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (26, 17);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (26, 18);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (27, 19);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (27, 20);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (40, 21);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (40, 22);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (41, 23);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (41, 24);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (43, 25);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (43, 26);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (44, 27);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (44, 28);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (46, 29);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (46, 30);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (47, 31);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (47, 32);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (30, 33);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (30, 34);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (31, 35);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (31, 36);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (33, 37);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (33, 38);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (34, 39);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (34, 40);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (36, 41);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (36, 42);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (37, 43);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (37, 44);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (3, 45);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (3, 46);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (4, 47);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (4, 48);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (6, 49);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (6, 50);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (7, 51);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (7, 52);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (9, 53);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (9, 54);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (10, 55);
INSERT INTO public.t_category_products (categories_id, products_id) VALUES (10, 56);