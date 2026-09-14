function solution(n, submit) {
  // 두개를 비교해서 카운트를 반환
  const dummy_submit = (origin, compare) => {
    let ten = 1000;
    let oSet = new Set();
    let cArr = [];
    // S
    let strike = 0;
    while (origin > 0) {
      let o = origin % 10;
      oSet.add(o);
      origin = Math.floor(origin / 10);
      let c = compare % 10;
      cArr.push(c);
      compare = Math.floor(compare / 10);
      if (o === c) strike++;
    }
    // B
    let ball = -1 * strike;
    cArr.forEach((v, i) => {
      if (oSet.has(v)) ball++;
    });

    return `${strike}S ${ball}B`;
  };

  // 제출가능한 숫자배열인지 체크
  const filter = (number) => {
    let s = new Set();
    while (number > 0) {
      let v = number % 10;
      if (v === 0) return false;
      s.add(v);
      number = Math.floor(number / 10);
    }
    if (s.size !== 4) return false;
    return true;
  };

  // 후보군 채우기
  let candi = [];
  for (let n = 1234; n < 9877; n++) {
    if (filter(n)) candi.push(n);
  }
  let answer = candi[0];

  for (let k = 0; k < 6; k++) {
    // 제출하고
    let result = submit(answer);
    if (result === "4S 0B") break;
    // 결과에 합당한 후보군 줄이고
    let nCandi = candi.filter((v, i) => {
      if (i === 0) return false;
      if (dummy_submit(answer, v) === result) return true;
      return false;
    });
    candi = nCandi;
    // answer 갱신하고 다시 진행
    answer = candi[0];
  }

  return answer;
}
