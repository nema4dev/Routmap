% Places.
place(parque_torogoz).
place(parque_colibri).
place(parque_chilamate).
place(parque_dichoso_fui).
place(parque_nascapilo).
place(parque_las_gardemias).
place(parque_el_cedro).
place(parque_villa_de_san_patricio).
place(parque_pijullo).
place(parque_la_ceiba).
place(parque_infantil_san_patricio).
place(parque_san_sebatian).
place(parque_zona_descanso).
place(terapian_fisica_natural_heling).
place(decotative_party_mark).
place(tienda_my_market).
place(clinica_denta_luis_fuente).
place(centro_escolar_san_patricio).

% Coordinates.
coordinate(parque_torogoz, 13.668056218947093, -89.21032185974566).
coordinate(parque_colibri, 13.668849235663949, -89.20916924532587).
coordinate(parque_chilamate, 13.668867221256534, -89.20916391697797).
coordinate(parque_dichoso_fui,13.669887829222667, -89.211457088798).
coordinate(parque_nascapilo, 13.669301818387726, -89.21035251744726).
coordinate(parque_las_gardemias,13.667631269577855, -89.21256081888203).
coordinate(parque_el_cedro, 13.666780151501662, -89.21117563224335).
coordinate(parque_villa_de_san_patricio,13.663924169169075, -89.20977897561966).
coordinate(parque_pijullo,13.668990984333139, -89.20794652458342).
coordinate(parque_la_ceiba,13.663919322955458, -89.21211057770839).
coordinate(parque_infantil_san_patricio,13.665894587320306, -89.21214055402432).
coordinate(parque_san_sebatian, 13.666012187883446, -89.21233958718328).
coordinate(parque_zona_descanso,13.66583157848624, -89.21247284017358).
coordinate(terapian_fisica_natural_heling,13.663787101120736, -89.2102047507637).
coordinate(decotative_party_mark,13.66800557020794, -89.21228325326422).
coordinate(tienda_my_market, 13.668197129691523, -89.21140885315533).
coordinate(clinica_denta_luis_fuente,13.669180049280396, -89.20975746549537).
coordinate(centro_escolar_san_patricio,13.67022841734383, -89.21204744376256).
coordinate(calle_dichoso_fuixcalle_del_torogoz,13.66932009511189, -89.2102752781383).
coordinate(calle_dichoso_fuixbld_san_patricio, 13.669026080349214, -89.20916758076754).
coordinate(calle_del_torogozxcalle_torogoz_2, 13.666877085482625, -89.21031971654934).
coordinate(calle_torogoz_2xave_torogoz_sur, 13.666780978014106, -89.21223213117942).
coordinate(ave_torogoz_surxave_torogoz_sur_2, 13.66590645497544, -89.212344570763).
coordinate(ave_torogoz_sur_2xave_torogoz_sur_3, 13.66533339835298, -89.2108606379999).
coordinate(ave_torogoz_sur_3xbld_san_patricio_3,13.663811325176862, -89.21071043466175).
coordinate(bld_san_patricioxbld_san_patricio_2xcalle_gavilan, 13.666067094537656, -89.20913341610313).
coordinate(calle_gavilanxpsj_chilamate, 13.666834964832415, -89.2075549310916).
coordinate(bld_san_patricio_2xbld_san_patricio_3, 13.663978443649942, -89.20974642767679).
coordinate(calle_dichoso_fuixcalle_dichoso_fui_3, 13.669639240589062, -89.2114040305268).
coordinate(calle_del_torogoz_2xpsj_gardemias, 13.66684468573883, -89.21163405459022).
coordinate(psj_las_gardemiasxpsj_las_gardemias_2, 13.667322674810094, -89.21194641339899).

% Conection Parque Torogoz y Parque Colibrí.
conects(parque_torogoz,calle_dichoso_fuixcalle_del_torogoz).
conects(calle_dichoso_fuixcalle_del_torogoz,calle_dichoso_fuixbld_san_patricio).
conects(calle_dichoso_fuixbld_san_patricio,parque_colibri).

% Conection entre Parque Colibrí y Parque Torogoz.
conects(parque_colibri,calle_dichoso_fuixbld_san_patricio).
conects(calle_dichoso_fuixbld_san_patricio,calle_dichoso_fuixcalle_del_torogoz).
conects(calle_dichoso_fuixcalle_del_torogoz,parque_torogoz).

% Predicate to get route
find_route(Start, End, Route) :-
    find_route(Start, End, [Start], Route).

find_route(Start, Start, [Start | Rest], Route) :- reverse([Start | Rest], Route), !.

find_route(Start, End, Visited, Route) :-
    conects(Start, Next),
    not(member(Next, Visited)),
    find_route(Next, End, [Next | Visited], Route).

get_place_names([], []).

get_place_names([Place | Rest], [Name | Names]) :-
    place(Name, _, _),
    Place = Name,
    get_place_names(Rest, Names).