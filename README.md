# JPA 연관관계 정리(team-adc 스터디 자료)

## 연관관계 설정
- 기본적으로 단방향 설정이 가능한 경우 단방향으로 설정하는것이 좋다.
- 연관관계의 주인을 설정해야하는데 연관관계의 주인은 FK를 갖고 있는 쪽으로 설정한다.

## @OneToMany
### 기본 개념
- 기본 fetch 전략 : LAZY (지연로딩)
- Client(1) 와 Campaign(多)의 관계
- 多를 갖기 때문에 컬렉션 (List, Set) 을 필드로 갖는다.
- 多 쪽 엔티티에 정의된 1쪽 엔티티의 `자바 필드명` 을 `mappedBy` 옵션의 값으로 넣어준다. (Clients.java:33)
- 단방향으로 사용하지 않고, 주로 ManyToOne과 짝으로 양방향으로 사용된다. 
> OneToMany 단방향 단점
> (https://ocblog.tistory.com/70)
### N+1
- Client를 조회할 경우 Client 테이블 조회 쿼리만 나가게 된다. (LAZY 전략)  
이 상태에서 Clients 내의 List<Campaign>의 내부의 값에 접근할 경우  
Campaigns 에 대한 조회쿼리가 나가게 되고 여기서 N+1의 문제가 발생한다.  
(예제: ClientRelationShiptTest 1번)

### fetch join
- N+1를 해결하기 위해 fetch join을 사용한다. (예제: ClientRelationShiptTest 2번)
- 단순 fetch join을 했을 경우 문제점은 多쪽 테이블 데이터 개수를 기준으로 1쪽 엔티티 개수가 뻥튀기된다.
- 이를 해결하기 위해 jpql에서 `distinct` 키워드를 사용할 경우 1쪽에 뻥튀기 된 엔티티가 정상적인 개수로 맞춰진다.

> hibernate 6.0 부터는 이부분이 개선되어 `distinct` 가 기본적으로 적용된다고 함.
> 
> DISTINCT
Starting with Hibernate ORM 6 it is no longer necessary to use distinct in JPQL and HQL to filter out the same parent entity references when join fetching a child collection. The returning duplicates of entities are now always filtered by Hibernate.
> 
> (https://docs.jboss.org/hibernate/orm/6.0/migration-guide/migration-guide.html#query-sqm-distinct)


### @ManyToOne
- 기본 fetch 전략 : EAGER (즉시 로딩)
- Campaigns(多)과 Clients(1)의 관계
- `@ManyToOne`의 경우 기본적으로 단방향이 가능하면 단방향으로 설계하는것이 좋다.
- `@JoinColumn(name)` 을 통해 1쪽 엔티티와 조인할 `FK db 필드명` 을 옵션에 넣어주어야 한다.(Campaigns.java:23)
- 기본 fetch 전략이 `EAGER` 이기 떄문에 <b>반드시</b> `fetch` 옵션을 `LAZY` 로 변경해준다

### @OneToOne
- 기본 fetch 전략 : EAGER (즉시 로딩)
- Clients(1)와 FileInfo(1)의 관계, Client(1)와 ExtraFeature(1)의 관계
- `@OneToOne`의 경우에도 기본적으로 단방향이 가능하면 단방향으로 설계하는것이 좋다. (Clients -> Accounts)
- `@OneToOne`은 특히 LAZY 로딩이 작동하지 않거나(양방향) 우리 프로젝트에서 사용중인 @Where 애노테이션이 적용되지 않는 등
  다양한 문제가 있다.
- 기본 fetch 전략이 `EAGER` 이기 떄문에 <b>반드시</b> `fetch` 옵션을 `LAZY` 로 변경해준다

> @OneToOne 문제점  
> https://www.inflearn.com/questions/40670

### @ManyToMany
- 사용을 웬만하면 지양한다.
- Banners(多) : Creatives(多)의 관계
- 보통 중간테이블을 통해 연결하는데 이 중간테이블을 다음과 같이 통해 1:多:1 관계로 풀어내는 것이 좋다.
  Banners(1) : BannerCreatives(多) : Creatives(1)
  (예제: BannerCreativesTest)
- `@JoinTable`을 설정하면 중간 테이블을 통해 다대다 조인이 된다.

> @ManyToMany 문제점
> 
> https://codeung.tistory.com/254
> https://dblog94.tistory.com/entry/JPA-Entity%EC%9D%98-NM-%EA%B4%80%EA%B3%84%EB%A5%BC-%EA%B0%9C%EB%B0%9C%ED%95%98%EB%A9%B4%EC%84%9C-%EB%8A%90%EB%82%80-%EA%B2%83

