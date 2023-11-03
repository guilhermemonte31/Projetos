import pygame
from pygame.locals import *

pygame.init()

screen_width = 600
screen_height = 500


fpsClock = pygame.time.Clock()

screen = pygame.display.set_mode((screen_width, screen_height))
pygame.display.set_caption("Pong")

#fonte
font = pygame.font.SysFont('Constantia', 30)

#variaveis do jogo
live_ball = False
margin = 50
player_score = 0
player2_score = 0
fps = 60
winner = 0
speed_increase = 0

#definindo cor
bg = (50,25,50)
white = (255, 255, 255)


def draw_board():
    screen.fill(bg) 
    pygame.draw.line(screen, white, (0, margin), (screen_width, margin))


def draw_text(text, font, text_color, x, y):
    img = font.render(text, True, text_color)
    screen.blit(img, (x, y))


class Paddle():
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.rect = Rect(self.x, self.y, 20, 100)
        self.speed = 5

    def move(self, option): #1 pra mover com seta e 2 pra mover com WASD
        key = pygame.key.get_pressed()
        if option == 1:
            if key[pygame.K_UP] and self.rect.top > margin:
                self.rect.move_ip(0, self.speed * (-1))
            if key[pygame.K_DOWN] and self.rect.bottom < screen_height:
                self.rect.move_ip(0, self.speed)
        elif option == 2:
            if key[pygame.K_w] and self.rect.top > margin:
                self.rect.move_ip(0, self.speed * (-1))
            if key[pygame.K_s] and self.rect.bottom < screen_height:
                self.rect.move_ip(0, self.speed)          

    def draw(self):
        pygame.draw.rect(screen, white, self.rect)


class Ball():
    def __init__(self, x, y):
        self.reset(x, y)

    def move(self):

        #colisao com o topo da margem
        if self.rect.top<margin:
            self.speed_y *= -1

        #colisao com o fundo da margem
        if self.rect.bottom>screen_height:
            self.speed_y *= -1

        if self.rect.colliderect(player_paddle) or self.rect.colliderect(player2_paddle):
            self.speed_x *= -1

        #checar se bateu nas paredes
        if self.rect.left < 0:
            self.winner = 1
        if self.rect.right > screen_width:
            self.winner = -1
        
        self.rect.x += self.speed_x
        self.rect.y += self.speed_y
        return self.winner;

    def draw(self):
        pygame.draw.circle(screen, white, (self.rect.x + self.ball_rad, self.rect.y + self.ball_rad), self.ball_rad)

    def reset(self, x, y):
        self.x = x
        self.y = y
        self.ball_rad = 8
        self.rect = Rect(self.x, self.y, self.ball_rad*2, self.ball_rad*2)
        self.speed_x = -4
        self.speed_y= 4
        self.winner = 0



#create paddles
player2_paddle = Paddle(screen_width - 40, screen_height//2)
player_paddle = Paddle(20, screen_height//2)


pong = Ball(screen_width - 60, screen_height//2 +50)

run = True
while run:
    fpsClock.tick(fps)
    draw_board()
    draw_text('P1 ' + str(player_score), font, white, 20, 15)
    draw_text('P2 ' + str(player2_score), font, white, screen_width - 100, 15)
    draw_text('BALL SPEED: '+ str(abs(pong.speed_x)), font, white, screen_width//2 - 100, 15)
    
    player_paddle.draw()
    player2_paddle.draw()

    if live_ball:
        speed_increase += 1
        winner = pong.move()
        if winner ==0:
            
            player2_paddle.move(1)
            player_paddle.move(2)
            pong.draw()
        else:
            live_ball = False
            if winner == 1:
                player2_score += 1
            elif winner == -1:
                player_score += 1


    if live_ball == False:
        if winner == 0:
            draw_text('CLICK ANYWHERE TO START', font, white, 100, screen_height//2 - 100)
        if winner == 1:
            draw_text('P2 SCORED!', font, white, 220, screen_height//2 - 100)
            draw_text('CLICK ANYWHERE TO START', font, white, 100, screen_height//2 - 50)
        
        if winner == -1:
            draw_text('P1 SCORED!', font, white, 220, screen_height//2 - 100)
            draw_text('CLICK ANYWHERE TO START', font, white, 100, screen_height//2 - 50)
        
    if speed_increase > 500:
        speed_increase = 0
        if pong.speed_x < 0:
            pong.speed_x -= 1
        if pong.speed_x > 0:
            pong.speed_x += 1
        if pong.speed_y < 0:
            pong.speed_y -= 1
        if pong.speed_y > 0:
            pong.speed_y += 1


    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False
        if event.type == pygame.MOUSEBUTTONDOWN and live_ball == False:
            live_ball = True
            pong.reset(screen_width-60, screen_height//2 + 50)
    pygame.display.update()

pygame.quit()