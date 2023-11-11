% Places.
place(parque_torogoz, 13.668056218947093, -89.21032185974566).
place(parque_colibri, 13.668849235663949, -89.20916924532587).
place(parque_dichoso_fui,13.669887829222667, -89.211457088798).
place(parque_las_gardemias,13.667631269577855, -89.21256081888203).
place(parque_el_cedro, 13.666780151501662, -89.21117563224335).
place(parque_pijullo,13.669143902447644, -89.20792239583879).
place(parque_san_sebatian, 13.666012187883446, -89.21233958718328).
place(gasolinera_uno_pijullo,13.669014116361335, -89.20818474624576).
%place(terapian_fisica_natural_heling,13.663787101120736, -89.2102047507637).
place(decorative_party_mark,13.66800557020794, -89.21228325326422).
place(tienda_my_market, 13.668197129691523, -89.21140885315533).
place(clinica_denta_luis_fuente,13.669180049280396, -89.20975746549537).
place(centro_escolar_san_patricio,13.67022841734383, -89.21204744376256).
place(escuela_creativa,13.671779967910235, -89.21163511505114).
place(centro_escolar,13.667839589177229, -89.20666029731218).
place(ferreteria_jordi, 13.666355085647645, -89.20914400261711).

% Conections


connection(parque_torogoz,clinica_denta_luis_fuente).
connection(clinica_denta_luis_fuente,parque_torogoz).


connection(parque_el_cedro, parque_torogoz).

connection(parque_torogoz, tienda_my_market).
connection(tienda_my_market, parque_torogoz).

connection(tienda_my_market, decorative_party_mark).
connection(decorative_party_mark, tienda_my_market).

connection(parque_san_sebatian, parque_el_cedro).
connection(parque_el_cedro, parque_san_sebatian).

connection(parque_colibri, ferreteria_jordi).
connection(ferreteria_jordi, parque_colibri).

connection(clinica_denta_luis_fuente,parque_colibri).
connection(parque_colibri, clinica_denta_luis_fuente).

connection(clinica_denta_luis_fuente, gasolinera_uno_pijullo).
connection(gasolinera_uno_pijullo, clinica_denta_luis_fuente).

connection(gasolinera_uno_pijullo,parque_pijullo).
connection(parque_pijullo, gasolinera_uno_pijullo).

connection(clinica_denta_luis_fuente, parque_dichoso_fui).
connection(parque_dichoso_fui, clinica_denta_luis_fuente).

connection(centro_escolar_san_patricio, parque_dichoso_fui).
connection(parque_dichoso_fui, centro_escolar_san_patricio).

connection(escuela_creativa, centro_escolar_san_patricio).
connection(centro_escolar_san_patricio, escuela_creativa).



connection(ferreteria_jordi, terapian_fisica_natural_heling).
connection(terapian_fisica_natural_heling, ferreteria_jordi).

% Predicate to get route
find_route(Start, End, Route) :-
    find_route(Start, End, [Start], Route).

find_route(Start, Start, [Start | Rest], Route) :- reverse([Start | Rest], Route), !.

find_route(Start, End, Visited, Route) :-
    connection(Start, Next),
    not(member(Next, Visited)),
    find_route(Next, End, [Next | Visited], Route).