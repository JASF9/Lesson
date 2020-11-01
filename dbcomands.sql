
CREATE TABLE public.Users (
                email VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                name VARCHAR NOT NULL,
                image VARCHAR NOT NULL,
                CONSTRAINT emails PRIMARY KEY (email)
);


CREATE TABLE public.VideoMedia (
                emailvideo VARCHAR NOT NULL,
                videoU VARCHAR NOT NULL,
                videoName VARCHAR NOT NULL,
                CONSTRAINT videopk PRIMARY KEY (emailvideo)
);


ALTER TABLE public.VideoMedia ADD CONSTRAINT users_videos_fk
FOREIGN KEY (emailvideo)
REFERENCES public.Users (email)
ON DELETE CASCADE
ON UPDATE CASCADE
NOT DEFERRABLE;
