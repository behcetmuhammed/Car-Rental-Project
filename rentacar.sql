PGDMP                      |            rentacar    15.7    16.3 #               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                        0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            !           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            "           1262    17173    rentacar    DATABASE     }   CREATE DATABASE rentacar WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE rentacar;
                postgres    false            �            1259    17235    book    TABLE     \  CREATE TABLE public.book (
    book_id integer NOT NULL,
    book_car_id integer NOT NULL,
    book_name text NOT NULL,
    book_idno text NOT NULL,
    book_mpno text NOT NULL,
    book_mail text,
    book_strt_date date NOT NULL,
    book_fnsh_date date NOT NULL,
    book_prc integer NOT NULL,
    book_note text,
    book_case text NOT NULL
);
    DROP TABLE public.book;
       public         heap    postgres    false            �            1259    17234    book_book_id_seq    SEQUENCE     �   CREATE SEQUENCE public.book_book_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.book_book_id_seq;
       public          postgres    false    222            #           0    0    book_book_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.book_book_id_seq OWNED BY public.book.book_id;
          public          postgres    false    221            �            1259    17210    brand    TABLE     m   CREATE TABLE public.brand (
    brand_id integer NOT NULL,
    brand_name character varying(255) NOT NULL
);
    DROP TABLE public.brand;
       public         heap    postgres    false            �            1259    17209    brand_brand_id_seq    SEQUENCE     �   CREATE SEQUENCE public.brand_brand_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.brand_brand_id_seq;
       public          postgres    false    216            $           0    0    brand_brand_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.brand_brand_id_seq OWNED BY public.brand.brand_id;
          public          postgres    false    215            �            1259    17226    car    TABLE     �   CREATE TABLE public.car (
    car_id integer NOT NULL,
    car_model_id integer NOT NULL,
    car_color text NOT NULL,
    car_km integer NOT NULL,
    car_plate text NOT NULL
);
    DROP TABLE public.car;
       public         heap    postgres    false            �            1259    17225    car_car_id_seq    SEQUENCE     �   CREATE SEQUENCE public.car_car_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.car_car_id_seq;
       public          postgres    false    220            %           0    0    car_car_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.car_car_id_seq OWNED BY public.car.car_id;
          public          postgres    false    219            �            1259    17217    model    TABLE     �   CREATE TABLE public.model (
    model_id integer NOT NULL,
    model_brand_id integer NOT NULL,
    model_name text NOT NULL,
    model_type text NOT NULL,
    model_year text NOT NULL,
    model_fuel text NOT NULL,
    model_gear text NOT NULL
);
    DROP TABLE public.model;
       public         heap    postgres    false            �            1259    17216    model_model_id_seq    SEQUENCE     �   CREATE SEQUENCE public.model_model_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.model_model_id_seq;
       public          postgres    false    218            &           0    0    model_model_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.model_model_id_seq OWNED BY public.model.model_id;
          public          postgres    false    217            �            1259    17174    user    TABLE     �   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_name text NOT NULL,
    user_password text NOT NULL,
    user_role text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            {           2604    17238    book book_id    DEFAULT     l   ALTER TABLE ONLY public.book ALTER COLUMN book_id SET DEFAULT nextval('public.book_book_id_seq'::regclass);
 ;   ALTER TABLE public.book ALTER COLUMN book_id DROP DEFAULT;
       public          postgres    false    221    222    222            x           2604    17213    brand brand_id    DEFAULT     p   ALTER TABLE ONLY public.brand ALTER COLUMN brand_id SET DEFAULT nextval('public.brand_brand_id_seq'::regclass);
 =   ALTER TABLE public.brand ALTER COLUMN brand_id DROP DEFAULT;
       public          postgres    false    216    215    216            z           2604    17229 
   car car_id    DEFAULT     h   ALTER TABLE ONLY public.car ALTER COLUMN car_id SET DEFAULT nextval('public.car_car_id_seq'::regclass);
 9   ALTER TABLE public.car ALTER COLUMN car_id DROP DEFAULT;
       public          postgres    false    219    220    220            y           2604    17220    model model_id    DEFAULT     p   ALTER TABLE ONLY public.model ALTER COLUMN model_id SET DEFAULT nextval('public.model_model_id_seq'::regclass);
 =   ALTER TABLE public.model ALTER COLUMN model_id DROP DEFAULT;
       public          postgres    false    217    218    218                      0    17235    book 
   TABLE DATA           �   COPY public.book (book_id, book_car_id, book_name, book_idno, book_mpno, book_mail, book_strt_date, book_fnsh_date, book_prc, book_note, book_case) FROM stdin;
    public          postgres    false    222   w%                 0    17210    brand 
   TABLE DATA           5   COPY public.brand (brand_id, brand_name) FROM stdin;
    public          postgres    false    216   �%                 0    17226    car 
   TABLE DATA           Q   COPY public.car (car_id, car_model_id, car_color, car_km, car_plate) FROM stdin;
    public          postgres    false    220   9&                 0    17217    model 
   TABLE DATA           u   COPY public.model (model_id, model_brand_id, model_name, model_type, model_year, model_fuel, model_gear) FROM stdin;
    public          postgres    false    218   �&                 0    17174    user 
   TABLE DATA           N   COPY public."user" (user_id, user_name, user_password, user_role) FROM stdin;
    public          postgres    false    214    '       '           0    0    book_book_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.book_book_id_seq', 18, true);
          public          postgres    false    221            (           0    0    brand_brand_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.brand_brand_id_seq', 9, true);
          public          postgres    false    215            )           0    0    car_car_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.car_car_id_seq', 4, true);
          public          postgres    false    219            *           0    0    model_model_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.model_model_id_seq', 6, true);
          public          postgres    false    217            �           2606    17242    book book_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (book_id);
 8   ALTER TABLE ONLY public.book DROP CONSTRAINT book_pkey;
       public            postgres    false    222                       2606    17215    brand brand_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.brand
    ADD CONSTRAINT brand_pkey PRIMARY KEY (brand_id);
 :   ALTER TABLE ONLY public.brand DROP CONSTRAINT brand_pkey;
       public            postgres    false    216            �           2606    17233    car car_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_pkey PRIMARY KEY (car_id);
 6   ALTER TABLE ONLY public.car DROP CONSTRAINT car_pkey;
       public            postgres    false    220            �           2606    17224    model model_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.model
    ADD CONSTRAINT model_pkey PRIMARY KEY (model_id);
 :   ALTER TABLE ONLY public.model DROP CONSTRAINT model_pkey;
       public            postgres    false    218            }           2606    17180    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    214               m   x�3��4�J��T�-�H��MM�445564�01�4052417243�*q �FFƺf�� �	�i�idl`���_�P����P�zt~jv��=E9�E�)�y�\1z\\\ $��         5   x�3�t��2����..OLO����M-JNMI-�2�J�K,�)����� ,�         O   x����0��g
&0��ջD=Tl�����=�!c��k=ٵ�B����`v��U9*���`�p��T蛈�S��         x   x�3�4�t���W0��ptvrt��4202�tw����s�t��2�4�H,.N,�vuq��14A��u�u��2���M�LJL΀+32��	p��b�i�a�d��%���k��D>F��� �\!*            x�3�L��,�4426�LL�������� G     