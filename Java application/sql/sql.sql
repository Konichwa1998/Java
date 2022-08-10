use JavaProjekt

create table Movie
(
	Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	Naziv nvarchar(100),
	OpisFilma nvarchar(MAX),
	Redatelj nvarchar(100),
	Zanr nvarchar(1000),
	PicturePath NVARCHAR(200)
)

alter table Movie
add PicturePath nvarchar(200)

alter procedure createMovie
	@Id int output,
	@Naziv nvarchar(100),
	@OpisFilma nvarchar(MAX),
	@Redatelj nvarchar(100),
	@Zanr nvarchar(1000),
	@Glumci nvarchar(100),
	@PicturePath nvarchar(200)
as
begin
	insert into Movie(Naziv, OpisFilma, Redatelj, Zanr, Glumci, PicturePath)
	values(@Naziv, @OpisFilma, @Redatelj, @Zanr, @Glumci, @PicturePath)
	SET @Id = SCOPE_IDENTITY()
end

alter procedure updateMovie
	@Id int,
	@Naziv nvarchar(100),
	@OpisFilma nvarchar(MAX),
	@Redatelj nvarchar(100),
	@Zanr nvarchar(1000),
	@Glumci nvarchar(100),
	@PicturePath nvarchar(200)
as
begin
	Update Movie set
		Naziv = @Naziv,
		OpisFilma = @OpisFilma,
		Redatelj = @Redatelj,
		Zanr = @Zanr,
		Glumci = @Glumci,
		PicturePath = @PicturePath
	where
		Id = @Id
end

create procedure updateDirector
	@Id int,
	@Ime nvarchar(100),
	@Prezime nvarchar(100)
as
begin
	Update Redatelj set
		Ime = @Ime,
		Prezime = @Prezime
	where
		Id = @Id
end

create procedure updateActor
	@Id int,
	@Ime nvarchar(100),
	@Prezime nvarchar(100)
as
begin
	Update Glumac set
		Ime = @Ime,
		Prezime = @Prezime
	where
		Id = @Id
end

create procedure deleteMovie
	@Id int
as
begin
	delete
	from
		Movie
	where
		Id = @Id
		
end

create table Glumac
(
	Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	Ime nvarchar(100),
	Prezime nvarchar(100)
)

create procedure createActor
	@Id int output,
	@Ime nvarchar(100),
	@Prezime nvarchar(100)
as
begin
	insert into Glumac(Ime, Prezime)
	values(@Ime, @Prezime)
	SET @Id = SCOPE_IDENTITY()
end

create procedure createDirector
	@Id int output,
	@Ime nvarchar(100),
	@Prezime nvarchar(100)
as
begin
	insert into Redatelj(Ime, Prezime)
	values(@Ime, @Prezime)
	SET @Id = SCOPE_IDENTITY()
end

create table Redatelj
(
	Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	Ime nvarchar(100),
	Prezime nvarchar(100)
)

create procedure deleteDirector
	@Id int
as
begin
	delete
	from
		Redatelj
	where
		Id = @Id
		
end

create procedure deleteActor
	@Id int
as
begin
	delete
	from
		Glumac
	where
		Id = @Id
		
end

create table FilmskaEkipa
(
	Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	MovieId INT FOREIGN KEY REFERENCES Movie(Id),
	GlumacId INT FOREIGN KEY REFERENCES Glumac(Id)
)

create table RedateljiFilma
(
	Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	MovieId INT FOREIGN KEY REFERENCES Movie(Id),
	RedateljId INT FOREIGN KEY REFERENCES Redatelj(Id)
)

create procedure DirectorToMovie
	@movieId int,
	@redateljId int
as
begin
	insert into RedateljiFilma(MovieId, RedateljId)
	values (@movieId, @redateljId)
end

create procedure ActorToMovie
	@movieId int,
	@glumacId int
as
begin
	insert into FilmskaEkipa(MovieId, GlumacId)
	values (@movieId, @glumacId)
end

create procedure DeleteActorFromMovie
	@movieId INT,
	@glumacId INT
as
begin
	delete from FilmskaEkipa
	where MovieId = @movieId and GlumacId = @glumacId
end

alter procedure DeleteDirectorFromMovie
	@movieId INT,
	@redateljId INT
as
begin
	delete from RedateljiFilma
	where MovieId = @movieId and RedateljId = @redateljId
end


create procedure GetActorForMovieById
	@MovieId int
as
begin
	select Glumac.Ime, Glumac.Prezime, Glumac.Id 
	from FilmskaEkipa 
	INNER JOIN Glumac on
	FilmskaEkipa.GlumacId = Glumac.Id
	where FilmskaEkipa.MovieId = @MovieId
end

create procedure GetDirectorForMovieById
	@MovieId int
as
begin
	select Redatelj.Ime, Redatelj.Prezime, Redatelj.Id 
	from RedateljiFilma 
	INNER JOIN Redatelj on
	RedateljiFilma.RedateljId = Redatelj.Id
	where RedateljiFilma.MovieId = @MovieId
end

CREATE PROCEDURE selectMovie
	@Id INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Movie
	WHERE 
		Id = @Id
END
GO

CREATE PROCEDURE selectActor
	@Id INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Glumac
	WHERE 
		Id = @Id
END
GO

CREATE PROCEDURE selectDirector
	@Id INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Redatelj
	WHERE 
		Id = @Id
END
GO

CREATE PROCEDURE selectMovies
AS 
BEGIN 
	SELECT * FROM Movie
END
GO

CREATE PROCEDURE selectActors
AS 
BEGIN 
	SELECT * FROM Glumac
END
GO

CREATE PROCEDURE selectDirectors
AS 
BEGIN 
	SELECT * FROM Redatelj
END
GO

create procedure deleteAllMovies
as
begin
	DELETE FROM Movie
end
go

create procedure GlumciRedatelji
as
begin
	
end
go

create table JavaAdmin
(
	Username nvarchar(100),
	Pass nvarchar(100)
)

create table JavaUser
(
	Username nvarchar(100),
	Pass nvarchar(100)
)

create proc CreateJavaUser
	@Username nvarchar(100),
	@Pass nvarchar(100)
as
begin
	insert into JavaUser(Username, Pass)
	values (@Username, @Pass)
end

create procedure AdminLogin

as
begin
	select * from JavaAdmin where Username='admin' and Pass='123'
end
go

create proc AuthUser
	@Username nvarchar(100),
	@Pass nvarchar(100)
as
begin
	SELECT * FROM JavaUser where Username = @Username and Pass = @Pass
end


insert into JavaAdmin
values ('admin','123')

insert into Redatelj
values('karlo','maros')

select * from Movie
select * from Glumac
select * from JavaAdmin
select * from RedateljiFilma
select * from JavaUser




