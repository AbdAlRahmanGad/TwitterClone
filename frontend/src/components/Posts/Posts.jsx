import style from "./Posts.module.css";
export function Posts() {
  return (
    <>
    <Post />
    <Post />
    <Post />
    <Post />
    <Post />
    </>
  );
}
function Post(){
  return (
    <div className={style.post}>
    <div className={style.userInfo}>
      <img src="/src/assets/img-1.jpg" alt="" className="profilePicture" />
      <span className="username">Ahmed Kashkoush</span>
      <span className="date">April 6, 2024</span>
    </div>

    <p className={style.content}>
      Lorem ipsum dolor sit amet, consectetur adipisicing elit. Inventore
      corporis nam provident? Commodi beatae, corporis dolor minus rem dolore
      minima. Lorem ipsum dolor sit amet, consectetur adipisicing elit.
      Inventore corporis nam provident? Commodi beatae, corporis dolor minus
      rem dolore minima. Lorem ipsum dolor sit amet, consectetur adipisicing
      elit. Inventore corporis nam provident? Commodi beatae, corporis dolor
      minus rem dolore minima.
    </p>

    
  </div>
  );
}
