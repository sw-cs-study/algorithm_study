function solution(m, n, startX, startY, balls) {
  var answer = [];

  const getOneCusionDistance = (p1, p2) => {
    // 4면으로 싹다 원쿠션 시킴, 해당 방향으로 쿠션맞출때, 공을 먼저 맞추는 경우면 뺌
    let ret = Infinity;
    // 위쪽
    if (!(p1[0] === p2[0] && p1[1] < p2[1]))
      ret = Math.min(
        ret,
        Math.abs(p1[0] - p2[0]) ** 2 + (n - p1[1] + n - p2[1]) ** 2,
      );

    // 아래쪽
    if (!(p1[0] === p2[0] && p1[1] > p2[1]))
      ret = Math.min(ret, Math.abs(p1[0] - p2[0]) ** 2 + (p1[1] + p2[1]) ** 2);

    // 오른쪽
    if (!(p1[0] < p2[0] && p1[1] === p2[1]))
      ret = Math.min(
        ret,
        (m - p1[0] + m - p2[0]) ** 2 + Math.abs(p1[1] - p2[1]) ** 2,
      );

    // 왼쪽
    if (!(p1[0] > p2[0] && p1[1] === p2[1]))
      ret = Math.min(ret, (p1[0] + p2[0]) ** 2 + Math.abs(p1[1] - p2[1]) ** 2);

    return ret;
  };

  let p1 = [startX, startY];
  balls.forEach((p2) => {
    answer.push(getOneCusionDistance(p1, p2));
  });

  return answer;
}
